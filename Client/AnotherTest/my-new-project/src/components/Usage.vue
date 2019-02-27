<template>
  <div>
    <div class="d-flex justify-between align-center mb-3">
      <v-btn @click="all">all</v-btn>
      <v-btn @click="none">none</v-btn>
    </div>

    <v-expansion-panel v-if="detections.length > 0" v-model="panel" expand>
      <v-expansion-panel-content v-for="(detection, i) in detections" :key="i">
        <div slot="header">{{ detection.ID }}</div>
        <v-card>
          <v-layout row wrap>
            <v-flex d-flex xs12 sm6 md8>
              <v-card color="purple" dark>
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
                  <v-layout row wrap>
                    <v-flex d-flex xs12 sm6 md5>
                      <span class="infoTitle">האם המטוס שלנו:</span><br />
                      <span class="infoDetail">{{ detection.IS_OURS }}</span>
                    </v-flex>
                    <v-flex d-flex xs12 sm6 offset-md1 md5>
                      <span class="infoTitle">האם המטוס ייורט:</span><br />
                      <span class="infoDetail">{{
                        detection.IS_SHUTDOWN
                      }}</span>
                    </v-flex>
                  </v-layout>
                </v-card-text>
              </v-card>
            </v-flex>
            <v-flex d-flex xs12 sm6 md4>
              <v-card color="blue lighten-2" dark>
                <v-card-text>
                  תמונה
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
export default {
  props: ['detections', 'items'],
  data() {
    return {
      panel: [],
    }
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
.infoTitle {
  font-size: 12px;
  align: right;
  font-weight: bold;
}
.infoDetail {
  font-size: 12px;
  align: right;
}
.v-card__text {
  margin: auto !important;
  text-align: center !important;
  display: table !important;
  vertical-align: middle !important;
}
</style>
