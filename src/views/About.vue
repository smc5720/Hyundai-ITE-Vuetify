<!-- 컴포넌트 UI 정의 -->
<template>
  <v-card class="mx-auto" width="500px">
    <v-img src="@/assets/photos/photo3.jpg" height="250px" />
    <v-card-title>About</v-card-title>
    <v-card-subtitle>This is about page.</v-card-subtitle>
    <v-card-text
      >Lorem ipsum dolor sit amet consectetur adipisicing elit. Error delectus ratione repudiandae
      porro? Blanditiis sunt beatae voluptatum nulla officiis ducimus odit, saepe deleniti iusto
      voluptatem sed provident, magnam molestias consequatur.</v-card-text
    >
    <v-card-actions>
      <div class="mx-auto">
        <v-btn class="mr-2" color="green" text>OK</v-btn>
        <v-btn color="red" text>Cancel</v-btn>
      </div>
    </v-card-actions>
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
