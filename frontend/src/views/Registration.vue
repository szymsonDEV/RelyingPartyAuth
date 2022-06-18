<template>
  <div class="main container fluid col-3">
      <h3>Sign-up</h3>
      <form class="mt-5">
        <input type="hidden" name="sex"/>
        <div class="form-group">
          <label for="usernameInput">Username</label>
          <input type="text" class="form-control" id="usernameInput" placeholder="Enter your username" v-model="formObject.username">
        </div>
        <div class="form-group">
          <label for="passwordInput">Password</label>
          <input type="password" class="form-control" id="passwordInput" placeholder="Enter your password" v-model="formObject.password">
        </div>
        <button type="button" v-on:click="onSubmit" class="btn btn-primary">Register</button>
      </form>
  </div>
</template>

<script>

export default {
  name: 'RegistrationView',
  components: {
  },
  data() {
    return {
      formObject: {}
    }
  },
  methods: {
    onSubmit() {
      this.axios
      .post('api/register', this.formObject, {headers: { 'Access-Control-Allow-Origin': 'http://localhost:8080','Content-Type': 'application/json' }})
      .then(result => {
        const bearerToken = result.headers.authorization;
        if (bearerToken && bearerToken.slice(0, 7) === 'Bearer ') {
          const jwt = bearerToken.slice(7, bearerToken.length);
          this.$sessionStorage.set("authenticationToken", jwt);
        }
      })
      .catch(e => {
        console.log("Authentication error:");
        console.log(e);
      });
    }
  }
}
</script>

<style>
</style>
