import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'

// https://vite.dev/config/
export default defineConfig({
  plugins: [react()],
  build: {
    outDir: '../backend/src/main/resources/static', // Output to Spring Boot's static directory
    emptyOutDir: true, // Ensure the output directory is cleared before each build
    assetsDir: '', // Specify where to place assets (will be same dir as index.html)
  },
  base: './',
})
