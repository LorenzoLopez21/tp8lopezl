package icop.tp8lopezl.productos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import icop.tp8lopezl.productos.entity.Producto;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
List<Producto> findByNombre(String nombre);
}