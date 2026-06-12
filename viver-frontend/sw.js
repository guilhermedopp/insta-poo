const CACHE_NAME = 'viver-mais-v1';
const ASSETS_TO_CACHE = [
  './',
  './index.html',
  './cadastro.html',
  './feed.html',
  './perfil.html',
  './publicar.html',
  './css/style.css',
  './js/api.js',
  './js/pwa-setup.js',
  './img/logo.png'
];

// Instala o Service Worker e guarda os ficheiros em cache
self.addEventListener('install', event => {
  event.waitUntil(
    caches.open(CACHE_NAME).then(cache => cache.addAll(ASSETS_TO_CACHE))
  );
});

// Responde aos pedidos da rede (permite abrir a app offline)
self.addEventListener('fetch', event => {
  event.respondWith(
    caches.match(event.request).then(response => response || fetch(event.request))
  );
});