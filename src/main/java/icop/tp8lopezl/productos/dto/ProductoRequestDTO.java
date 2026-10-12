package icop.tp8lopezl.productos.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductoRequestDTO {
    private String nombre;
    private Double precio;
    private Integer stock;
}
