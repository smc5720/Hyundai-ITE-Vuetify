<!-- 컴포넌트 UI 정의 -->
<template>
  <v-app>
    <v-navigation-drawer app color="secondary" dark v-model="drawer">
      <v-list-item>
        <v-list-item-content>
          <v-list-item-title class="text-h6"> Application </v-list-item-title>
          <v-list-item-subtitle>with Vuetify</v-list-item-subtitle>
        </v-list-item-content>
      </v-list-item>

      <v-list dense nav>
        <v-list-item to="/">
          <v-list-item-icon>
            <v-icon>mdi-home</v-icon>
          </v-list-item-icon>
          <v-list-item-content>
            <v-list-item-title>Home</v-list-item-title>
          </v-list-item-content>
        </v-list-item>

        <v-list-item to="/about">
          <v-list-item-icon>
            <v-icon>mdi-help-circle</v-icon>
          </v-list-item-icon>
          <v-list-item-content>
            <v-list-item-title>About</v-list-item-title>
          </v-list-item-content>
        </v-list-item>

        <v-list-group value="true" prepend-icon="mdi-palette-outline" active-class="white--text">
          <template v-slot:activator>
            <v-list-item-content>
              <v-list-item-title>Styles</v-list-item-title>
            </v-list-item-content>
          </template>

          <v-list-item to="/styles/textandtypography" color="white">
            <v-list-item-icon></v-list-item-icon>
            <v-list-item-content>
              <v-list-item-title>Text and Typography</v-list-item-title>
            </v-list-item-content>
          </v-list-item>

          <v-list-item to="/styles/spacing" color="white">
            <v-list-item-icon></v-list-item-icon>
            <v-list-item-content>
              <v-list-item-title>Margin and Padding</v-list-item-title>
            </v-list-item-content>
          </v-list-item>
        </v-list-group>

        <v-list-item to="/gridsystem">
          <v-list-item-icon> <v-icon>mdi-view-grid-outline</v-icon></v-list-item-icon>
          <v-list-item-content>
            <v-list-item-title>Grid System</v-list-item-title>
          </v-list-item-content>
        </v-list-item>
      </v-list>
    </v-navigation-drawer>

    <v-app-bar app color="secondary" dark>
      <v-app-bar-nav-icon @click="drawer = !drawer"></v-app-bar-nav-icon>
      <v-toolbar-title>Collapsing Bar</v-toolbar-title>
    </v-app-bar>

    <v-main>
      <v-container fluid>
        <router-view></router-view>
      </v-container>
    </v-main>

    <v-footer app color="secondary" dark>
      <div class="mx-auto">S.M.C</div>
    </v-footer>
  </v-app>
</template>

<script>
export default {
  // component의 대표 이름(devtools에 표시되는 이름)
  name: "App",
  // 추가하고 싶은 컴포넌트를 등록
  components: {},
  // 컴포넌트 데이터 정의
  data: function () {
    return { drawer: true };
  },
  // 컴포넌트 메소드 정의
  methods: {},
};
</script>

<!-- 컴포넌트 스타일 정의 -->
<style scoped></style>
