<template>
<nav class="navbar navbar-expand-lg" style="background-color: #ffffff;">
  <div class="container-fluid">
    <div class="collapse navbar-collapse" id="navbarSupportedContent">
      <ul class="navbar-nav me-auto mb-2 mb-lg-0">
      </ul>
      <form class="d-flex">
        <b-button v-if="$auth.isAuthenticated()" variant="primary">Logout</b-button>
        <b-button v-if="!$auth.isAuthenticated()" variant="primary" :to="{name: 'Login'}">Login</b-button>
        <b-button v-if="!$auth.isAuthenticated()" variant="primary" :to="{name: 'Registration'}" class="ms-2">Register</b-button>
      </form>
    </div>
  </div>
</nav>
</template>

<script>
export default {
  data () {
    return {
    }
  }
}
</script>

<style>

</style>
