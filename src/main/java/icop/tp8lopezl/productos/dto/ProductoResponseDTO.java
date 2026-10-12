package icop.tp8lopezl.productos.dto;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ProductoResponseDTO {
    private Long id;
    private String nombre;
    private Double precio;
    private Integer stock;
    private boolean disponible;

    public ProductoResponseDTO(Long id, String nombre, Double precio, Integer stock) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
        this.disponible = stock > 0;
    }
}