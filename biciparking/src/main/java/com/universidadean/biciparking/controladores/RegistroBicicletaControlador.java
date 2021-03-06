package com.universidadean.biciparking.controladores;

import com.universidadean.biciparking.modelo.RegistroBicicleta;
import com.universidadean.biciparking.servicios.RegistroBicicletaServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
public class RegistroBicicletaControlador {
    @Autowired
    private RegistroBicicletaServicio servicio;

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/registroBicicleta")
    public ResponseEntity<String> crearResgistroBicicleta(@Valid @RequestBody RegistroBicicleta registroBicicleta){

        Long idRegistroBicicleta = servicio.crearRegistroBicicleta(registroBicicleta);
        return new ResponseEntity<>("Se creo el registro exitosamente id:" + idRegistroBicicleta, HttpStatus.CREATED);
    }
    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/registroBicicleta/{identificacion}/{serialBici}")
    public @ResponseBody  RegistroBicicleta obtenerResgistroBicicleta(@PathVariable Long identificacion, @PathVariable String serialBici){
        return servicio.obtenerRegistroBicicleta(identificacion,serialBici);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/registroBicicleta/{id}")
    public @ResponseBody  Optional<RegistroBicicleta> obtenerResgistroBicicleta(@PathVariable Long id){
        return servicio.obtenerRegistroBicicleta(id);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/registrosBicicletas")
    public @ResponseBody  Iterable<RegistroBicicleta> obtenerResgistrosBicicletas(){
        return servicio.obtenerRegistrosBicicletas();
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/salidaRegistroBicicleta/{id}")
    public @ResponseBody  ResponseEntity<String> salidaRegistroBicicleta(@PathVariable Long id){
        Long idRegistroBicicleta = servicio.registrarFechaSalidaBicicleta(id);
        return new ResponseEntity<>("Se registro la salida exitosamente id:" + idRegistroBicicleta, HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @DeleteMapping("/registroBicicleta/{id}")
    public @ResponseBody  ResponseEntity<String> eliminarRegistro(@PathVariable Long id){
        servicio.eliminarRegistro(id);
        return new ResponseEntity<>("Se elimino el registro exitosamente", HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
