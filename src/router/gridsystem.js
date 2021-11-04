export default [
  {
    path: "/styles/textandtypography",
    component: () => import(/* webpackChunkName: "styles" */ "../views/styles/TextAndTypography"),
  },
  {
    path: "/styles/spacing",
    component: () => import(/* webpackChunkName: "styles" */ "../views/styles/Spacing"),
  },
];
