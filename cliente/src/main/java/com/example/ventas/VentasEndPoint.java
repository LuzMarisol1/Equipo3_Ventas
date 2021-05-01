package com.example.ventas;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import me.tell.ventas.EliminarVentaRequest;
import me.tell.ventas.EliminarVentaResponse;
import me.tell.ventas.ModificarVentaRequest;
import me.tell.ventas.ModificarVentaResponse;
import me.tell.ventas.MostrarVentaResponse;
import me.tell.ventas.RealizarVentaRequest;
import me.tell.ventas.RealizarVentaResponse;


@Endpoint
public class VentasEndPoint {
    

  @Autowired
  private IVentas iventas;

  @PayloadRoot(namespace = "http://tell.me/ventas", localPart = "RealizarVentaRequest")

  @ResponsePayload
  public RealizarVentaResponse guardaVenta(@RequestPayload RealizarVentaRequest peticion) {

    RealizarVentaResponse respuesta = new RealizarVentaResponse();
    respuesta.setRespuesta("Venta Realizada " + peticion.getNombre());

    Productosv productos = new Productosv();
    productos.setNombre(peticion.getNombre());
    productos.setDescripcion(peticion.getDescripcion());
    productos.setPrecioU(peticion.getPrecioU());
    productos.setEnStock(peticion.getEnStock());
    iventas.save(productos);

    return respuesta;
  }

  @PayloadRoot(namespace = "http://tell.me/ventas", localPart = "MostrarVentaRequest")

    @ResponsePayload
    public MostrarVentaResponse dameventas(){
      MostrarVentaResponse respuesta = new MostrarVentaResponse();
    Iterable<Productosv> listaVentas = iventas.findAll();

    for(Productosv ls : listaVentas){
    
    MostrarVentaResponse.EmpleadoV saludador = new MostrarVentaResponse.EmpleadoV();
    saludador.setIdproducto(ls.getIdproducto());
    saludador.setNombre(ls.getNombre());
    saludador.setDescripcion(ls.getDescripcion());
    saludador.setPrecioU(ls.getPrecioU());
    saludador.setEnStock(ls.getEnStock());
    respuesta.getEmpleadoV().add(saludador);
  }
  return respuesta;
}

@PayloadRoot(namespace = "http://tell.me/ventas", localPart = "ModificarVentaRequest")

   @ResponsePayload
   public ModificarVentaResponse modificarsaludo(@RequestPayload ModificarVentaRequest peticion){
    ModificarVentaResponse respuesta = new ModificarVentaResponse();

      Optional<Productosv> s = iventas.findById(peticion.getIdproducto());
      
      if(s.isPresent()){
        Productosv productosv = new Productosv();
          productosv = s.get();
          productosv.setNombre(peticion.getNombre());
          productosv.setPrecioU(peticion.getPrecioU());
          respuesta.setRespuesta("Se modifico " + peticion.getNombre());
          iventas.save(productosv);
        }else{
            respuesta.setRespuesta("Id no existe " + peticion.getIdproducto());

        } 
        return respuesta;
    }

  @PayloadRoot(namespace = "http://tell.me/ventas",localPart = "EliminarVentaRequest")

  @ResponsePayload
  public EliminarVentaResponse borrarsaludo(
    @RequestPayload EliminarVentaRequest peticion) {
    EliminarVentaResponse respuesta = new EliminarVentaResponse();
    respuesta.setRespuesta("Eliminado correctamente el id " + peticion.getId());
    //validad que no existe
    iventas.deleteById(peticion.getId());
    return respuesta;
  }
  
  
  }