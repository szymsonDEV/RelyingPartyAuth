<template>
  <div class="main container fluid col-3">
      <h3>Sign-in</h3>
      <b-form class="mt-5 mb-5">
        <input type="hidden" name="sex"/>
        <b-form-group id="input-group-2" label="Username:" label-for="usernameInput">
          <b-form-input
            id="usernameInput"
            v-model="formObject.username"
            placeholder="Enter username"
            required>
          </b-form-input>
        </b-form-group>
        <b-form-group id="input-group-2" label="Password:" label-for="passwordInput">
          <b-form-input
            id="passwordInput"
            v-model="formObject.password"
            placeholder="Enter password"
            type="password"
            required>
          </b-form-input>
        </b-form-group>
      </b-form>
      <button type="button" v-on:click="onLogin" class="btn btn-primary">Login</button>
      <hr class="hr-text" data-content="OR">
      <button type="button" v-on:click="onFastLogin" class="btn btn-primary">Fast login</button>
  </div>
</template>

<script>
import * as webauthnJson from "@github/webauthn-json";

export default {
  name: 'LoginView',
  components: {
  },
  data() {
    return {
      formObject: {}
    }
  },
  methods: {
    async onFastLogin () {

      this.axios.get('api/v1/u2f/login/start').then(resp => {
        const credentialGetOptions = resp.data;
        webauthnJson.get(JSON.parse(credentialGetOptions.publicKeyCredentialRequestOptionsJSON)).then(publicKeyCredential => {
          this.axios.post('api/v1/u2f/login/finish', {credential: publicKeyCredential, requestId: credentialGetOptions.requestId}).then((resp) => {
            const jwt = resp.data;
            if (jwt) {
              sessionStorage.setItem("sdev_authenticationToken", jwt);
            }
            this.$router.push('/account')
          })
        })
      });
    },

    async onLogin () {
      this.axios
      .post('api/authenticate', this.formObject)
      .then(result => {
        const bearerToken = result.headers.authorization;
        if (bearerToken && bearerToken.slice(0, 7) === 'Bearer ') {
          const jwt = bearerToken.slice(7, bearerToken.length);
          sessionStorage.setItem("sdev_authenticationToken", jwt);
        }
        this.$store.dispatch('loadAccount');
        this.$router.push('/account');
      })
      .catch(e => {
        console.log("Authentication error:");
        console.log(e);
      });
    },

  }
}
</script>

<style scoped>
.hr-text {
	line-height: 1em;
	position: relative;
	outline: 0;
	border: 0;
	color: white;
	text-align: center;
	height: 1.5em;
	opacity: 0.5;
}
 .hr-text:before {
	content: '';
	background: linear-gradient(to right, transparent, #818078, transparent);
	position: absolute;
	left: 0;
	top: 50%;
	width: 100%;
	height: 1px;
}
 .hr-text:after {
	content: attr(data-content);
	position: relative;
	display: inline-block;
	color: black;
	padding: 0 0.5em;
	line-height: 1.5em;
	color: #818078;
	background-color: #fcfcfa;
}
 
</style>
