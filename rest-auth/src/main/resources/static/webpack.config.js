const { loadFiles } = require("./load-webpack-files");
const path = require("path");
console.log(loadFiles);
module.exports = {
  entry: loadFiles,
  output: {
    filename: "[name]",
    path: path.resolve(__dirname, "dist"),
    clean: true, // To clean the dist folder before each build
  },
  module: {
    rules: [
      {
        test: /\.css$/,
        use: ["style-loader", "css-loader"],
      },
    ],
  },
  devServer: {
    headers: {
      "Access-Control-Allow-Origin": "*",
      "Access-Control-Allow-Methods": "GET, POST, PUT, DELETE, PATCH, OPTIONS",
      "Access-Control-Allow-Headers":
        "X-Requested-With, content-type, Authorization",
    },
    static: path.resolve(__dirname, "."),
    allowedHosts: "all",
    hot: true,
    port: 9000,
  },
  resolve: {
    modules: ["node_modules"],
  },
};
