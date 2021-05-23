package isi.dan.laboratorios.danmspedidos.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import isi.dan.laboratorios.danmspedidos.domain.DetallePedido;
import isi.dan.laboratorios.danmspedidos.domain.Pedido;

import isi.dan.laboratorios.danmspedidos.domain.Pedido;

@RestController
@RequestMapping(PedidoRest.API_PEDIDO)
public class PedidoRest {
    static final String API_PEDIDO = "/api/pedido";
    
    private static final List<Pedido> listaPedidos = new ArrayList<Pedido>();
    private static Integer ID_GEN = 1;
    private static Integer indexPedido;

    @GetMapping(path = "/{idPedido}")
    public ResponseEntity<Pedido> pedidoPorId(@PathVariable Integer idPedido){

        Optional<Pedido> p =  listaPedidos
                .stream()
                .filter(unPe -> unPe.getId().equals(idPedido))
                .findFirst();
        return ResponseEntity.of(p);
    }

    @GetMapping
    public ResponseEntity<List<Pedido>> todos(){
        return ResponseEntity.ok(listaPedidos);
    }

    //GET por id de obra
    @GetMapping(path = "/obra/{idObra}")
    public ResponseEntity<Pedido> pedidoPorIdObra(@PathVariable Integer idObra){

        Optional<Pedido> p =  listaPedidos
                .stream()
                .filter(unPe -> unPe.getObra().getId().equals(idObra))
                .findFirst();
        return ResponseEntity.of(p);
    }

    //FALTA GET Por Cuit y/o ID de Cliente
   /* @GetMapping(path = "/nombre")
    @ResponseBody
    public ResponseEntity<List<Pedido>> pedidoPorCuitOIdCliente(@RequestParam(required = false) Integer cuit, @RequestParam(required = false)  Integer id) {

        List<Pedido> o =  listaPedidos
                .stream()
                .filter(unPedido -> (unPedido.getCliente().getId().equals(id)) || (unPedido.getCliente().getCuit().equals(cuit)))
                .collect(Collectors.toList());

        return ResponseEntity.ok(o);
    }*/


    @GetMapping(path = "/{idPedido}/detalle/{id}")
    public ResponseEntity<DetallePedido> pedidoPorDetalle(@PathVariable Integer idPedido, @PathVariable Integer id){

        DetallePedido detallePedido = new DetallePedido();

        for(int i=0; i<listaPedidos.size(); i++){
            for(int j=0; j<listaPedidos.get(i).getDetallesPedido().size(); j++){
                if(listaPedidos.get(i).getId().equals(idPedido) && listaPedidos.get(i).getDetallesPedido().get(j).getId().equals(id)){
                    detallePedido = listaPedidos.get(i).getDetallesPedido().get(j);
                    break;
                }
            }
        }

        return ResponseEntity.ok(detallePedido);
       
    }

    @PostMapping
    public ResponseEntity<Pedido> crear(@RequestBody Pedido nuevo){
    	System.out.println("Crear pedido "+ nuevo);
        nuevo.setId(ID_GEN++);
        listaPedidos.add(nuevo);
        return ResponseEntity.ok(nuevo);
    }

    @PostMapping(path = "/{idPedido}/detalle")
    public ResponseEntity<Pedido> crearItem(@RequestBody DetallePedido nuevoItem, @PathVariable Integer idPedido){
    	System.out.println("Crear item " + nuevoItem + " a pedido de id: " + idPedido);
        Pedido pedido = null;
        for(int i=0; i<listaPedidos.size(); i++){
            
            if(listaPedidos.get(i).getId().equals(idPedido)){
                listaPedidos.get(i).setItemDetallePedido(nuevoItem);
                pedido = listaPedidos.get(i);
                break;
            }

        }
        return ResponseEntity.ok(pedido);
    }

    @PutMapping(path = "/{idPedido}")
    public ResponseEntity<Pedido> actualizar(@RequestBody Pedido nuevo,  @PathVariable Integer idPedido) {
        OptionalInt indexOpt =   IntStream.range(0, listaPedidos.size())
        .filter(i -> listaPedidos.get(i).getId().equals(idPedido))
        .findFirst();

        if(indexOpt.isPresent()){
            listaPedidos.set(indexOpt.getAsInt(), nuevo);
            return ResponseEntity.ok(nuevo);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(path = "/{idPedido}")
    public ResponseEntity<Pedido> borrar(@PathVariable Integer idPedido){
        OptionalInt indexOpt =   IntStream.range(0, listaPedidos.size())
        .filter(i -> listaPedidos.get(i).getId().equals(idPedido))
        .findFirst();

        if(indexOpt.isPresent()){
            listaPedidos.remove(indexOpt.getAsInt());
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(path = "/{idPedido}/detalle/{id}")
    public ResponseEntity<Pedido> borrarDetalle(@PathVariable Integer idPedido, @PathVariable Integer id){
        //final int indexPedido = 0;
        for(int i=0; i<listaPedidos.size(); i++){
            
            if(listaPedidos.get(i).getId().equals(idPedido)){
                indexPedido = i;
                break;
            }
        } 
        
        OptionalInt indexOpt =   IntStream.range(0, listaPedidos.get(indexPedido).getDetallesPedido().size())
        .filter(i -> listaPedidos.get(indexPedido).getDetallesPedido().get(i).getId().equals(id))
        .findFirst();

        if(indexOpt.isPresent()){
            listaPedidos.get(indexPedido).getDetallesPedido().remove(indexOpt.getAsInt());
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }



}