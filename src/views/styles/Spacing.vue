<!-- 컴포넌트 UI 정의 -->
<template>
  <v-card>
    <v-card-title>Margin and Padding</v-card-title>
    <v-card-text>
      <div class="yellow pa-3">
        <div class="indigo lighten-3 white--text ml-10 mr-5">
          Adjust screen size to see spacing changes
        </div>
        <div class="red lighten-3 white--text ml-10 mr-5 mt-5 pa-5">
          Adjust screen size to see spacing changes
        </div>
      </div>
    </v-card-text>
  </v-card>
</template>

<script>
export default {
  // component의 대표 이름(devtools에 표시되는 이름)
  name: "",
  // 추가하고 싶은 컴포넌트를 등록
  components: {},
  // 컴포넌트 데이터 정의
  data: function () {
    return {};
  },
  // 컴포넌트 메소드 정의
  methods: {},
};
</script>

<!-- 컴포넌트 스타일 정의 -->
<style scoped></style>
