import globals from "globals";
import pluginJs from "@eslint/js";


export default [
  {languageOptions: { globals: globals.browser }},
  pluginJs.configs.recommended,
  {
    rules: {
      semi: ['error', 'never']
    }
  },
  {
    ignores: [
      "src/main/resources/static/js",
      "build/resources/main/static/js"
    ]
  },
];