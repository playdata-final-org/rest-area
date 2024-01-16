const fs = require("fs");
const path = require("path");

function getFiles(path) {
  return fs
    .readdirSync(path, { recursive: true })
    .filter((filename) => {
      let fileinfo = fs.statSync(`${path}/${filename}`);
      return fileinfo.isFile();
    })
    .map((filename) => `${path}/${filename}`);
}

const loadFiles = getFiles("./src").reduce((acc, file) => {
  const entryName = file.replace(/^\.\/src\//, "");
  acc[entryName] = path.resolve(__dirname, file);
  return acc;
}, {});

module.exports = { loadFiles };
