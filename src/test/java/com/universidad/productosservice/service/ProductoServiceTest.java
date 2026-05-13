package com.universidad.productosservice.service;

import com.universidad.productosservice.domain.Producto;
import com.universidad.productosservice.repository.ProductoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductoServiceTest {

    @Mock
    private ProductoRepository productoRepository;

    @InjectMocks
    private ProductoService productoService;

    private Producto producto;

    @BeforeEach
    void setUp() {
        producto = new Producto();
        producto.setId(1L);
        producto.setNombre("Teclado Mecánico");
        producto.setPrecio(250000.0);
        producto.setStock(10);
    }

    @Test
    void buscarDebeRetornarProductoCuandoExiste() {
        when(productoRepository.findById(1L)).thenReturn(Optional.of(producto));

        Producto resultado = productoService.buscar(1L);

        assertNotNull(resultado);
        assertEquals("Teclado Mecánico", resultado.getNombre());
    }

    @Test
    void buscarDebeLanzarExcepcionCuandoNoExiste() {
        when(productoRepository.findById(99L)).thenReturn(Optional.empty());

        NoSuchElementException ex = assertThrows(NoSuchElementException.class,
                () -> productoService.buscar(99L));

        assertEquals("Producto no encontrado: 99", ex.getMessage());
    }

    @Test
    void procesarProductoDebeGuardarProductoValido() {
        when(productoRepository.save(any(Producto.class))).thenAnswer(inv -> inv.getArgument(0));

        Producto guardado = productoService.procesarProducto("  Monitor  ", 800000.0, 5);

        assertEquals("Monitor", guardado.getNombre());
        assertEquals(800000.0, guardado.getPrecio());
        assertEquals(5, guardado.getStock());
        verify(productoRepository).save(any(Producto.class));
    }

    @Test
    void procesarProductoDebeFallarSiNombreEsInvalido() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> productoService.procesarProducto("   ", 1000.0, 2));

        assertEquals("El nombre no puede estar vacío", ex.getMessage());
    }

    @Test
    void procesarProductoDebeFallarSiPrecioEsInvalido() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> productoService.procesarProducto("Mouse", 0.0, 2));

        assertEquals("El precio debe ser mayor a cero", ex.getMessage());
    }

    @Test
    void procesarProductoDebeFallarSiStockEsNegativo() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> productoService.procesarProducto("Mouse", 120000.0, -1));

        assertEquals("El stock no puede ser negativo", ex.getMessage());
    }
}
