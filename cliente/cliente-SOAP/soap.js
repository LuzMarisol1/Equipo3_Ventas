var ventas;
var elimp;
var mod;

function agregar() {
    ventas = '<Envelope xmlns="http://schemas.xmlsoap.org/soap/envelope/">' +
        '<Body>' +
        '<RealizarVentaRequest xmlns="http://tell.me/ventas">' +
        '<nombre>' + document.getElementById('nombre').value + '</nombre>' +
        '<descripcion>' + document.getElementById('descripcion').value + '</descripcion>' +
        '<precioU>' + document.getElementById('precioU').value + '</precioU>' +
        '<enStock>' + document.getElementById('enStock').value + '</enStock>' +
        '</RealizarVentaRequest>' +
        '</Body>' +
        '</Envelope>'
}
function venta() {
    agregar()
    axios.post('http://localhost:8080/ws/venta', ventas, {
        headers: {
            'Content-Type': 'text/xml',
            'SOAPAction': ''
        },
    })
    alert("Registrado correctamente");
    location.href = "/cliente/cliente-SOAP/html/RealizarVenta.html"
}
/**ELIMINAR */
function eliminar() {
    elimp = '<Envelope xmlns="http://schemas.xmlsoap.org/soap/envelope/">' +
        '<Body>' +
        '<EliminarVentaRequest xmlns="http://tell.me/ventas">' +
        '<id>' + document.getElementById('id').value + '</id>' +
        '</EliminarVentaRequest>' +
        '</Body>' +
        '</Envelope>'
}

function idElim() {
    eliminar()
    axios.post('http://localhost:8080/ws/venta', elimp, {
        headers: {
            'Content-Type': 'text/xml',
            'SOAPAction': ''
        },
    })
    alert("Eliminado correctamente");
    location.href = "/cliente/cliente-SOAP/html/Eliminar.html"
}

function visualizar() {
    verVentas = '<Envelope xmlns="http://schemas.xmlsoap.org/soap/envelope/">' +
        '<Body>' +
        '<MostrarVentaResponse xmlns="http://tell.me/ventas">' +
        '</MostrarVentaRequest>'
    '</Envelope>'
    console.log(verVentas);
}

function visualizarV() {
    visualizar()
    axios.get('http://localhost:8080/ws/venta', verVentas, {
        headers: {

        }
    })
}

function soap() {
    mod = '<Envelope xmlns="http://schemas.xmlsoap.org/soap/envelope/">' +
        '<Body>' +
        '<ModificarVentaRequest xmlns="http://tell.me/ventas">' +
        '<idproducto>' + document.getElementById('idproducto').value + '</idproducto>' +
        '<nombre>' + document.getElementById('nombre').value + '</nombre>' +
        '<precioU>' + document.getElementById('precioU').value + '</precioU>' +
        '</ModificarVentaRequest>' +
        '</Body>' +
        '</Envelope>'
}
function modificarv() {
    soap();
    axios.post('http://localhost:8080/ws/ventas', mod, {
        headers: {
            'Content-Type': 'text/xml',
        },
    })
        .then(function (response) {
            console.log(response.data);
            document.getElementById("r").value = resul(response.data);
        })
        .catch(err => console.log(err));
}

function resul(rXml) {
    var parser = new DOMParser();
    var xmlDoc = parser.parseFromString(rXml, "text/xml");
    var resultado = xmlDoc.getElementsByTagName("ns2:respuesta")//[0].childNodes[0].nodeValue;
    console.log(resultado);
    return resultado;
}
var mensaje

function ini() {
    mensaje = '<Envelope xmlns="http://schemas.xmlsoap.org/soap/envelope/">' +
        '<Body>' +
        '<MostrarVentaRequest xmlns="http://tell.me/ventas">' +

        '</MostrarVentaRequest>' +
        '</Body>' +
        '</Envelope>'
}

function soap() {
    ini();
    axios
        .post('http://localhost:8080/ws/venta', mensaje, {
            headers: {
                "Content-Type": "text/xml",
            },
        })
        .then(function (response) {
            //   console.log(response.data);
            document.getElementById("r").value = resul(response.data);
            resultado(response.data)
        })
        .catch((err) => console.log(err));
}

function resultado(rXml) {
    var txt = '';
    var id, nombre, descripcion, precio, stock;
    var parser = new DOMParser();
    var xmlDoc = parser.parseFromString(rXml, "text/xml");
    var res = xmlDoc.getElementsByTagName("ns2:empleadoV");
    var resul = res.length;
    var tabla;
    for (i = 0; i < resul; i++) {
        id = xmlDoc.getElementsByTagName("ns2:idproducto")[i].childNodes[0].nodeValue;
        nombre = xmlDoc.getElementsByTagName("ns2:nombre")[i].childNodes[0].nodeValue;
        descripcion = xmlDoc.getElementsByTagName("ns2:descripcion")[i].childNodes[0].nodeValue;
        precio = xmlDoc.getElementsByTagName("ns2:precioU")[i].childNodes[0].nodeValue;
        stock = xmlDoc.getElementsByTagName("ns2:enStock")[i].childNodes[0].nodeValue;
        console.log("idproducto: " + id + " nombre: " + nombre + " descripcion: " + descripcion + " precioU: " + precio + " enStock: " + stock);

        tabla = tabla + "<tr>";
        tabla = tabla + "<td>" + id + "</td>";
        tabla = tabla + "<td>" + nombre + "</td>";
        tabla = tabla + "<td>" + descripcion + "</td>";
        tabla = tabla + "<td>" + precio + "</td>";
        tabla = tabla + "<td>" + stock + "</td>";
        tabla = tabla + "</tr>";
    }
    document.getElementById("tabla").innerHTML = tabla;

}
