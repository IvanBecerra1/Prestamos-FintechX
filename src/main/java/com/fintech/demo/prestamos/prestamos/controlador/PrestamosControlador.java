package com.fintech.demo.prestamos.prestamos.controlador;

import com.fintech.demo.prestamos.prestamos.dto.ListaPrestamosDTO;
import com.fintech.demo.prestamos.prestamos.dto.PrestamoDTO;
import com.fintech.demo.prestamos.prestamos.excepcion.PrestamoExcepcion;
import com.fintech.demo.prestamos.prestamos.modelo.Prestamos;
import com.fintech.demo.prestamos.prestamos.servicio.Impl.PrestamoServicioImpl;
import com.fintech.demo.prestamos.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.function.EntityResponse;

import java.util.List;

@RestController
@RequestMapping("/prestamos")
@RequiredArgsConstructor
public class PrestamosControlador {

    private final PrestamoServicioImpl solicitarPrestamoServicio;

    /**
     * Solicitud de Prestamo
     * @return
     */
    @PostMapping("/solicitar")
    public ResponseEntity<ApiResponse<Prestamos>> solicitarPrestamo(@RequestBody PrestamoDTO prestamos) {
        try {
            Prestamos creado = this.solicitarPrestamoServicio.solicitarPrestamo(prestamos);
            ApiResponse<Prestamos> response = new ApiResponse<>(
                true,
                    "prestamo solicitado, en estado pendiente",
                    creado
            );

            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (PrestamoExcepcion e) {
            ApiResponse<Prestamos> response = new ApiResponse<>(
                    false,
                    e.getMessage()
            );

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        catch (Exception ex){

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>(
                    false,
                    "Error interno en el servidor"
            ));
        }
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/listar-todos")
    public ResponseEntity<ApiResponse<List<ListaPrestamosDTO>>> misPrestamo(){
        try {
            List<ListaPrestamosDTO> lista =  this.solicitarPrestamoServicio.listarPrestamos();
            ApiResponse<List<ListaPrestamosDTO>> response = new ApiResponse<>(
                    true,
                    "Lista de prestamos",
                    lista
            );

            return  ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (PrestamoExcepcion ex){
            return   ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(
                    false,
                    ex.getMessage()
            ));
        }
        catch (Exception e){
            return    ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>(
               false,"Error interno del servidor"
            ));
        }
    }

    /**
     * Cancelar el prestamo del usuario
     * @param id
     * @return
     */
    @PostMapping("/cancelar/{id}")
    public String cancelarPrestamo(@PathVariable Integer id){
        return "";
    }

    /**
     * Listar los prestamos de un usuario
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public String listarPrestamos(@PathVariable Integer id){
        return "";
    }


    /**
     * Accion sobre el estado del prestamo.
     * Solo rol Admin
     * @param id : id del prestamo
     * @return
     */
    @PutMapping("/accion/{id}")
    public String accionPrestamos(@PathVariable Integer id){
        return "";
    }

    /**
     * Eliminar prestamo del BD
     * Solo rol admin
     * @param id
     * @return
     */
    @DeleteMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Integer id){
        return "";
    }


}
