package isi.dan.laboratorios.danmsusuarios.rest;

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
import isi.dan.laboratorios.danmsusuarios.domain.Empleado;
import isi.dan.laboratorios.danmsusuarios.domain.Obra;

@RestController
@RequestMapping(ObraRest.API_OBRA)
public class ObraRest {
    static final String API_OBRA = "/api/obra";
    
    private static final List<Obra> listaObras = new ArrayList<Obra>();
    private static Integer ID_GEN = 1;

    @GetMapping(path = "/{id}")
    public ResponseEntity<Obra> obraPorId(@PathVariable Integer id){

        Optional<Obra> o =  listaObras
                .stream()
                .filter(unaObra -> unaObra.getId().equals(id))
                .findFirst();
        return ResponseEntity.of(o);
    }

    @GetMapping
    public ResponseEntity<List<Obra>> todas(){
        return ResponseEntity.ok(listaObras);
    }

    // GET por id -> Retorna una única obra
    @GetMapping(path = "/id/{id}")
    public ResponseEntity<Obra> obraPorIdCliente(@PathVariable String id) {
        Optional<Obra> o =  listaObras
                .stream()
                .filter(unaObra -> unaObra.getCliente().getId().toString().equals(id)) 
                .findFirst();
        return ResponseEntity.of(o);
    }

    //GET por cliente y/o tipo de obra (query string OPC) -> Retorna una lista de obras
    @GetMapping(path = "/nombre")
    @ResponseBody
    public ResponseEntity<List<Obra>> obraPorClienteOTipoObra(
        @RequestParam(required = false) String idCliente, @RequestParam(required = false)  String tipoObra) {

        List<Obra> o =  listaObras
                .stream()
                .filter(unaObra -> (unaObra.getCliente().getId().toString().equals(idCliente)) || (unaObra.getTipo().getId().toString().equals(tipoObra)))
                .collect(Collectors.toList());

        return ResponseEntity.ok(o);
    }

    @PostMapping
    public ResponseEntity<Obra> crear(@RequestBody Obra nuevo){
    	System.out.println("Crear obra "+ nuevo);
        nuevo.setId(ID_GEN++);
        listaObras.add(nuevo);
        return ResponseEntity.ok(nuevo);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<Obra> actualizar(@RequestBody Obra nuevo,  @PathVariable Integer id) {
        OptionalInt indexOpt =   IntStream.range(0, listaObras.size())
        .filter(i -> listaObras.get(i).getId().equals(id))
        .findFirst();

        if(indexOpt.isPresent()){
            listaObras.set(indexOpt.getAsInt(), nuevo);
            return ResponseEntity.ok(nuevo);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Empleado> borrar(@PathVariable Integer id){
        OptionalInt indexOpt =   IntStream.range(0, listaObras.size())
        .filter(i -> listaObras.get(i).getId().equals(id))
        .findFirst();

        if(indexOpt.isPresent()){
            listaObras.remove(indexOpt.getAsInt());
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}