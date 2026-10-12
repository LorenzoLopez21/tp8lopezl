package icop.tp8lopezl.productos.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import icop.tp8lopezl.productos.dto.ProductoRequestDTO;
import icop.tp8lopezl.productos.dto.ProductoResponseDTO;
import icop.tp8lopezl.productos.entity.Producto;
import icop.tp8lopezl.productos.exception.ProductoNotFoundException;
import icop.tp8lopezl.productos.repository.ProductoRepository;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;

    // Spring inyecta el repositorio aquí
    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    public ProductoResponseDTO crear(ProductoRequestDTO dto) {
        validar(dto);
        Producto producto = toEntity(dto);
        Producto guardado = productoRepository.save(producto);
        return toResponseDTO(guardado);
    }

    public List<ProductoResponseDTO> listar() {
        List<Producto> productos = productoRepository.findAll();
        List<ProductoResponseDTO> respuesta = new ArrayList<>();

        for (Producto p : productos) {
            respuesta.add(toResponseDTO(p));
        }

        return respuesta;
    }

    public ProductoResponseDTO obtenerPorId(Long id) throws ProductoNotFoundException {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ProductoNotFoundException("no existe un producto con el id: " + id));
        return toResponseDTO(producto);
    }

    public ProductoResponseDTO actualizar(Long id, ProductoRequestDTO dto) throws ProductoNotFoundException {
        validar(dto);
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ProductoNotFoundException("no existe un producto con el id: " + id));

        producto.setNombre(dto.getNombre());
        producto.setPrecio(dto.getPrecio());
        producto.setStock(dto.getStock());

        Producto guardado = productoRepository.save(producto);
        return toResponseDTO(guardado);
    }

    public void eliminar(Long id) throws ProductoNotFoundException {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ProductoNotFoundException("no existe un producto con el id: " + id));
        productoRepository.delete(producto);
    }

    public List<ProductoResponseDTO> buscarPorNombre(String nombre) {
        List<Producto> productos = productoRepository.findByNombre(nombre);
        List<ProductoResponseDTO> respuesta = new ArrayList<>();

        for (Producto p : productos) {
            respuesta.add(toResponseDTO(p));
        }

        return respuesta;
    }

    private void validar(ProductoRequestDTO dto) {
        if (dto.getNombre() == null || dto.getNombre().isBlank()) {
            throw new IllegalArgumentException("el nombre no puede estar vacio ni ser nulo");
        }
        if (dto.getPrecio() == null || dto.getPrecio() <= 0) {
            throw new IllegalArgumentException("el precio debe ser mayor a 0");
        }
        if (dto.getStock() == null || dto.getStock() < 0) {
            throw new IllegalArgumentException("el stock no debe ser ni negativo ni nulo");
        }
    }

    private Producto toEntity(ProductoRequestDTO dto) {
        Producto producto = new Producto();
        producto.setNombre(dto.getNombre());
        producto.setPrecio(dto.getPrecio());
        producto.setStock(dto.getStock());
        return producto;
    }

    private ProductoResponseDTO toResponseDTO(Producto producto) {
        return new ProductoResponseDTO(
                producto.getIdProducto(),
                producto.getNombre(),
                producto.getPrecio(),
                producto.getStock());
    }
}
