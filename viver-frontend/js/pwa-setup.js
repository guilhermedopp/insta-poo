if ('serviceWorker' in navigator) {
  window.addEventListener('load', () => {
    navigator.serviceWorker.register('./sw.js')
      .then(registration => {
        console.log('ServiceWorker registado com sucesso!', registration.scope);
      })
      .catch(erro => {
        console.log('Falha ao registar o ServiceWorker:', erro);
      });
  });
}