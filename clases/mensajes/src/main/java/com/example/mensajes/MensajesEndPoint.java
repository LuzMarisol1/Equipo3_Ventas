package com.example.mensajes;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import me.tell.mensajes.BorrarSaludosRequest;
import me.tell.mensajes.BorrarSaludosResponse;
import me.tell.mensajes.BuscarSaludosRequest;
import me.tell.mensajes.BuscarSaludosResponse;
import me.tell.mensajes.ModificarSaludosRequest;
import me.tell.mensajes.ModificarSaludosResponse;
import me.tell.mensajes.SaludarRequest;
import me.tell.mensajes.SaludarResponse;


@Endpoint
public class MensajesEndPoint {
    @Autowired
    private Isaludadores isaludadores;

    @PayloadRoot(namespace = "http://tell.me/mensajes", localPart = "SaludarRequest")

    @ResponsePayload
    public SaludarResponse dameSaludo(@RequestPayload SaludarRequest peticion){
        SaludarResponse respuesta  = new SaludarResponse();
        respuesta.setRespuesta("Hola " + peticion.getNombre());

        Saludadores saludadores = new Saludadores();
        saludadores.setNombre(peticion.getNombre());
        isaludadores.save(saludadores);

        return respuesta;
    }

    @PayloadRoot(namespace = "http://tell.me/mensajes", localPart = "BuscarSaludosRequest")

    @ResponsePayload
    public BuscarSaludosResponse dameSaludadores(@RequestPayload BuscarSaludosRequest peticion){
        BuscarSaludosResponse respuesta = new BuscarSaludosResponse();
        Iterable<Saludadores>listaSaludadores = isaludadores.findAll();
        
        for(Saludadores ls : listaSaludadores){
            BuscarSaludosResponse.Saludador saludador = new  BuscarSaludosResponse.Saludador();
            saludador.setNombre(ls.getNombre());
            saludador.setId(ls.getId());
            respuesta.getSaludador().add(saludador);
        }
        return respuesta;
    }

    @PayloadRoot(namespace = "http://tell.me/mensajes", localPart = "ModificarSaludosRequest")

   @ResponsePayload
   public ModificarSaludosResponse modificarsaludo(@RequestPayload ModificarSaludosRequest peticion){
       ModificarSaludosResponse respuesta = new ModificarSaludosResponse();

        Optional<Saludadores> s = isaludadores.findById(peticion.getId());
        
        if(s.isPresent()){
            Saludadores saludadores = new Saludadores();
            saludadores = s.get();
            saludadores.setNombre(peticion.getNombre());
            isaludadores.save(saludadores);
            respuesta.setRespuesta("Se modifico " + peticion.getNombre());
        }else{
            respuesta.setRespuesta("i d no existe " + peticion.getId());
        }
        return respuesta;
    }
    
   @PayloadRoot(namespace = "http://tell.me/mensajes", localPart = "BorrarSaludosRequest")

   @ResponsePayload
   public BorrarSaludosResponse borrarsaludo (@RequestPayload BorrarSaludosRequest peticion){
        BorrarSaludosResponse respuesta = new BorrarSaludosResponse();
        respuesta.setRespuesta("Eliminado correctamente el id " + peticion.getId());
        //validad que no existe
        isaludadores.deleteById(peticion.getId());
        
        return respuesta;
    }
}
