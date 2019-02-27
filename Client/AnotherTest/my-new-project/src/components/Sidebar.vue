<template>
  <v-navigation-drawer
    class="blue lighten-3"
    dark
    permanent
    fixed
    style="background-color: #06425C !important;
  border-color: #06425C !important;"
  >
    <center><img :src="logoUrl" width="200" height="200"/></center>
    <v-list>
      <v-list-tile v-for="item in items" :key="item.title" @click="">
        <v-list-tile-action>
          <v-icon>{{ item.icon }}</v-icon>
        </v-list-tile-action>

        <v-list-tile-content>
          <v-list-tile-title>{{ item.title }}</v-list-tile-title>
        </v-list-tile-content>
      </v-list-tile>
    </v-list>
  </v-navigation-drawer>
</template>

<script>
export default {
  data() {
    return {
      items: [
        { title: 'אירועים', icon: 'dashboard' },
        { title: 'סטטיסטיקות', icon: 'account_box' },
        { title: 'הצגה בזמן אמת', icon: 'gavel' },
      ],
      logoUrl: require('../images/logo.png'),
    }
  },
}
</script>

<style lang="css" scoped>
.lighten-3 {
  /* position: fixed; */
  background-color: #666600 !important;
  border-color: #666600 !important;
}
img {
  text-align: center;
  margin-top: 10px;
}
</style>
