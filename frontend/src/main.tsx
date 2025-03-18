import { createRoot } from 'react-dom/client'
import './index.css'
import App from './App.tsx'
import {GlobalStateProvider} from "./config/GlobalState.tsx";

createRoot(document.getElementById('root')!).render(
  <GlobalStateProvider>
    <App />
  </GlobalStateProvider>,
)
