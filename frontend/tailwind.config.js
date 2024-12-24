/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    "./index.html",
    "./src/**/*.{vue,js,ts,jsx,tsx}",  // 这行配置确保 Tailwind CSS 会扫描 Vue 文件
  ],
  theme: {
    extend: {}
  },
  plugins: [],
}
