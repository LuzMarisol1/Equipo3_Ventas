var mensaje;
function ini() {
    mensaje = '<Envelope xmlns="http://schemas.xmlsoap.org/soap/envelope/">' +
        '<Body>' +
        '<SumarRequest xmlns="http://www.example.org/calculadora">' +
        '<a>'+ document.getElementById('a').value +'</a>' +
        '<b>'+ document.getElementById('b').value +'</b>' +
        '</SumarRequest>' +
        '</Body>' +
        '</Envelope>'
}

function soap() {
    ini()
    axios.post('http://localhost:8080/ws/calculadora', mensaje, {
        headers: {
            'Content-Type': 'text/xml',
            'SOAPAction': ''
        },
    })
        .then(function (response) {
            console.log(response.data)
            document.getElementById("r").value = resul(response.data);
        })
        .catch(err => console.log(err));
}

function resul(rXml){
   var parser = new DOMParser();
   var xmlDoc = parser.parseFromString(rXml, "text/xml");
   var resultado = xmlDoc.getElementsByTagName("ns2:resultado")[0].childNodes[0].nodeValue;
  return resultado;
}
