<template>
  <Layout>
    <h1>מערכת זי"ת</h1>
    <h3>READY על המפה</h3>
    <Usage :detections="detections" :items="detections.length"></Usage>
  </Layout>
</template>

<script>
import appConfig from '@src/app.config'
import axios from 'axios'
import Layout from '@layouts/main'
import Usage from '@src/components/Usage'

export default {
  page: {
    title: '',
    meta: [{ name: 'description', content: appConfig.description }],
  },
  components: { Layout, Usage },
  data() {
    return {
      detections: [],
    }
  },
  mounted() {
    this.loadDetections()
  },
  methods: {
    loadDetections() {
      axios.get('http://localhost:3000/detections').then((response) => {
        this.detections = this.detectionsAfterURLUpdate(response.data)
      })
    },
    detectionsAfterURLUpdate(detections) {
      let updatedDetections = detections
      for (
        let currDetection = 0;
        currDetection < detections.length;
        currDetection++
      ) {
        updatedDetections[currDetection]['IMAGE_URL'] = String(
          updatedDetections[currDetection]['IMAGE_URL']
        ).replace('\\\\', '\\')
      }
      return updatedDetections
    },
  },
}
</script>

<style lang="css">
.main_container_ImJwa {
  text-align: center;
  direction: rtl;
}
.v-expansion-panel__header {
  direction: rtl;
  text-align: right;
}
</style>
