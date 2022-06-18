<template>
  <app-nav/>
  <img width="500" alt="Vue logo" src="./assets/szymsonLOGO.svg">
  <router-view/>
</template>

<script>
import AppNav from './components/AppNav.vue'
export default {
  name: 'App',
  components: { AppNav },
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
