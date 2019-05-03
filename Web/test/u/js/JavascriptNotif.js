Notification.requestPermission( function(status) {
  console.log(status); // les notifications ne seront affichées que si "autorisées"
  var n = new Notification("title", {body: "notification body"}); // this also shows the notification
});


window.addEventListener('load', function () {
  // Premièrement, vérifions que nous avons la permission de publier des notifications. Si ce n'est pas le cas, demandons la
  if (window.Notification && Notification.permission !== "granted") {
    Notification.requestPermission(function (status) {
      if (Notification.permission !== status) {
        Notification.permission = status;
      }
    });
  }

  var button = document.getElementsByTagName('button')[0];

  -button.addEventListener('click', function () {
    // Si l'utilisateur accepte d'être notifié
    if (window.Notification && Notification.permission === "granted") {
      var n = new Notification("Coucou !");
    }

    // Si l'utilisateur n'a pas choisi s'il accepte d'être notifié
    // Note: à cause de Chrome, nous ne sommes pas certains que la propriété permission soit définie, par conséquent il n'est pas sûr de vérifier la valeur par défaut.
    else if (window.Notification && Notification.permission !== "denied") {
      Notification.requestPermission(function (status) {
        if (Notification.permission !== status) {
          Notification.permission = status;
        }

        // Si l'utilisateur est OK
        if (status === "granted") {
          var n = new Notification("Coucou !");
        }

        // Sinon, revenons en à un mode d'alerte classique
        else {
          alert("Coucou !");
        }
      });
    }

    // Si l'utilisateur refuse d'être notifié
    else {
      // We can fallback to a regular modal alert
      alert("Coucou !");
    }
  });
});

