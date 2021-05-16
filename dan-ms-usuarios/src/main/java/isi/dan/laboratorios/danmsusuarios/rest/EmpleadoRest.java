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

@RestController
@RequestMapping(EmpleadoRest.API_EMPLEADO)
public class EmpleadoRest {
    static final String API_EMPLEADO = "/api/empleado";
    
    private static final List<Empleado> listaEmpleados = new ArrayList<>();
    private static Integer ID_GEN = 1;

    @GetMapping(path = "/{id}")
    public ResponseEntity<Empleado> empleadoPorId(@PathVariable Integer id){

        Optional<Empleado> c =  listaEmpleados
                .stream()
                .filter(unCli -> unCli.getId().equals(id))
                .findFirst();
        return ResponseEntity.of(c);
    }

    @GetMapping
    public ResponseEntity<List<Empleado>> todos(){
        return ResponseEntity.ok(listaEmpleados);
    }

    // GET por id -> Retorna un único empleado
    @GetMapping(path = "/id/{id}")
    public ResponseEntity<Empleado> empleadoPorId(@PathVariable String id) {
        Optional<Empleado> e =  listaEmpleados
                .stream()
                .filter(unEmp -> unEmp.getId().toString().equals(id)) 
                .findFirst();
        return ResponseEntity.of(e);
    }

    // GET por nombre (query string OPC) -> Retorna una lista de empleados 
    @GetMapping(path = "/nombre")
    @ResponseBody
    public ResponseEntity<List<Empleado>> empleadoPorNombre(
        @RequestParam(required = false) String nombre) {
        List<Empleado> e =  listaEmpleados
                .stream()
                .filter(unEmp -> unEmp.getUser().getUser().equals(nombre))
                .collect(Collectors.toList());

        return ResponseEntity.ok(e);
    }

    @PostMapping
    public ResponseEntity<Empleado> crear(@RequestBody Empleado nuevo){
    	System.out.println("Crear empleado "+ nuevo);
        nuevo.setId(ID_GEN++);
        listaEmpleados.add(nuevo);
        return ResponseEntity.ok(nuevo);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<Empleado> actualizar(@RequestBody Empleado nuevo,  @PathVariable Integer id) {
        OptionalInt indexOpt =   IntStream.range(0, listaEmpleados.size())
        .filter(i -> listaEmpleados.get(i).getId().equals(id))
        .findFirst();

        if(indexOpt.isPresent()){
            listaEmpleados.set(indexOpt.getAsInt(), nuevo);
            return ResponseEntity.ok(nuevo);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Empleado> borrar(@PathVariable Integer id){
        OptionalInt indexOpt =   IntStream.range(0, listaEmpleados.size())
        .filter(i -> listaEmpleados.get(i).getId().equals(id))
        .findFirst();

        if(indexOpt.isPresent()){
            listaEmpleados.remove(indexOpt.getAsInt());
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}