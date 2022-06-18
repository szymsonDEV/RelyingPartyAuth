<template>
  <div class="main container fluid col-3" v-if="account">
      <p>Hello <b>{{account.username}}</b></p>
      <p>You have <b>{{account.registredCredentials}} registred credentials</b></p>
      <p><button type="button" v-on:click="doRegisterU2F" class="btn btn-primary">Register key</button></p>
  </div>
</template>

<script>
import {
  mapState
} from 'vuex'
import * as webauthnJson from "@github/webauthn-json";
export default {
  name: 'AccountView',
  components: {
  },
  // beforeRouteEnter(to, from, next) {
  //   getPost(to.params.id, (err, post) => {
  //     next(vm => vm.setData(err, post))
  //   })
  // },
  created () {
    this.$store.dispatch('loadAccount');
  },
  data() {
    return {
    }
  },
  methods: {
    async doRegisterU2F () {
      this.axios.get(`api/v1/u2f/register/start?username=${this.account.username}`)
        .then(resp => {
          const credentialCreateOptions = resp.data;
          webauthnJson.create(JSON.parse(credentialCreateOptions.publicKeyCredentialCreationOptionsJSON))
            .then(resp => {
              const publicKeyCredential = resp
              this.axios.post("api/v1/u2f/register/finish", {credential: publicKeyCredential, requestId: credentialCreateOptions.requestId}
              ).then(() => this.$store.dispatch('loadAccount'))
        })});
    },
  },
  computed: {
    ...mapState({
      account: state => state.account
    })
  }
}
</script>

<style>
</style>
