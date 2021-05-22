package isi.dan.laboratorios.danmspedidos.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.IntStream;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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

    @GetMapping(path = "/{idPedido}")
    public ResponseEntity<Pedido> pedidoPorId(@PathVariable Integer id){

        Optional<Pedido> p =  listaPedidos
                .stream()
                .filter(unPe -> unPe.getId().equals(id))
                .findFirst();
        return ResponseEntity.of(p);
    }

    @GetMapping
    public ResponseEntity<List<Pedido>> todos(){
        return ResponseEntity.ok(listaPedidos);
    }

    //GET por id de obra
    @ResponseBody
    public ResponseEntity<Pedido> pedidoPorIdObra(@PathVariable Integer id){

        Optional<Pedido> p =  listaPedidos
                .stream()
                .filter(unPe -> unPe.getObra().getId().toString().equals(id))
                .findFirst();
        return ResponseEntity.of(p);
    }

    //FALTA GET Por Cuit y/o ID de Cliente
    //FALTA GET Buscar detalle por ID: “/api/pedido/{idPedido}/detalle/{id}”


    @PostMapping
    public ResponseEntity<Pedido> crear(@RequestBody Pedido nuevo){
    	System.out.println("Crear pedido "+ nuevo);
        nuevo.setId(ID_GEN++);
        listaPedidos.add(nuevo);
        return ResponseEntity.ok(nuevo);
    }

    @PostMapping(path = "/{idPedido}/detalle")
    public ResponseEntity<Pedido> crearItem(@RequestBody DetallePedido nuevoItem, @RequestBody Pedido pedido){
    	System.out.println("Crear item " + nuevoItem + " a pedido " + pedido);
        
        for(int i=0; i<listaPedidos.size(); i++){
            
            if(listaPedidos.get(i).getId().equals(pedido.getId())){
                pedido.setItemDetallePedido(nuevoItem);
                break;
            }

        }
        return ResponseEntity.ok(pedido);
    }

    @PutMapping(path = "/{idPedido}")
    public ResponseEntity<Pedido> actualizar(@RequestBody Pedido nuevo,  @PathVariable Integer id) {
        OptionalInt indexOpt =   IntStream.range(0, listaPedidos.size())
        .filter(i -> listaPedidos.get(i).getId().equals(id))
        .findFirst();

        if(indexOpt.isPresent()){
            listaPedidos.set(indexOpt.getAsInt(), nuevo);
            return ResponseEntity.ok(nuevo);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(path = "/{idPedido}")
    public ResponseEntity<Pedido> borrar(@PathVariable Integer id){
        OptionalInt indexOpt =   IntStream.range(0, listaPedidos.size())
        .filter(i -> listaPedidos.get(i).getId().equals(id))
        .findFirst();

        if(indexOpt.isPresent()){
            listaPedidos.remove(indexOpt.getAsInt());
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //ACÁ FALTA DELETE POR DETALLE



}