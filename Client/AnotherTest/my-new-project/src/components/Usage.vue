<template>
  <div>
    <div class="d-flex justify-between align-center mb-3">
      <v-btn @click="all">הרחב הכל</v-btn>
      <v-btn @click="none">צמצם הכל</v-btn>
    </div>

    <v-expansion-panel v-if="detections.length > 0" v-model="panel" expand>
      <v-expansion-panel-content v-for="(detection, i) in detections" :key="i">
        <div slot="header">{{ detection.ID }}</div>
        <v-card>
          <v-layout row wrap>
            <v-flex d-flex xs12 sm6 md8>
              <v-card color="#06425C" dark>
                <v-card-title primary class="title"
                  >זיהוי מספר {{ detection.ID }}</v-card-title
                >
                <v-card-text>
                  <v-layout row wrap>
                    <v-flex d-flex xs12 sm6 md4>
                      <span class="infoTitle">זמן תיעוד:</span><br />
                      <span class="infoDetail">{{
                        detection.DETECTION_TIME
                      }}</span>
                    </v-flex>
                    <v-flex d-flex xs12 sm6 offset-md1 md3>
                      &nbsp;&nbsp;&nbsp;<span class="infoTitle"
                        >גובה:&nbsp;&nbsp;&nbsp;</span
                      ><br />
                      <span class="infoDetail">{{ detection.HEIGHT }}</span>
                    </v-flex>
                    <v-flex d-flex xs12 sm6 offset-md1 md3>
                      <span class="infoTitle">מרחק:</span><br />
                      <span class="infoDetail">{{ detection.DISTANCE }}</span>
                    </v-flex>
                  </v-layout>
                </v-card-text>
              </v-card>
            </v-flex>
            <v-flex d-flex xs12 sm6 md4>
              <v-card color="black lighten-2" dark>
                <v-card-text>
                  <img :src="detection['IMAGE_URL']" width="160" height="120" />
                </v-card-text>
              </v-card>
            </v-flex>
          </v-layout>
        </v-card>
      </v-expansion-panel-content>
    </v-expansion-panel>
  </div>
</template>

<script>
// import * as io from 'socket.io-client'
export default {
  props: ['detections', 'items'],
  data() {
    return {
      panel: [],
    }
  },
  mounted() {
    // const socket = io('localhost:3000')
  },
  methods: {
    // Create an array the length of our items
    // with all values as true
    all() {
      this.panel = [...Array(this.items).keys()].map((_) => true)
    },
    // Reset the panel
    none() {
      this.panel = []
    },
  },
}
</script>

<style lang="css" scoped>
.title {
  /* color: Black; */
}
.infoTitle {
  font-size: 12px;
  /* color: Black; */
  align: right;
  font-weight: bold;
}
.infoDetail {
  font-size: 12px;
  /* color: Black; */
  align: right;
}
.v-card__text {
  margin: auto !important;
  text-align: center !important;
  display: table !important;
  vertical-align: middle !important;
}
</style>
