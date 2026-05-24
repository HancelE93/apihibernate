package com.krakdev.peliculas.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.krakdev.peliculas.entidades.Pelicula;
import com.krakdev.peliculas.services.ServicioPelicula;


@RestController
@RequestMapping("/peliculas")
public class PeliculaController {
	
	private final ServicioPelicula servicio;

	public PeliculaController(ServicioPelicula servicio) {
		super();
		this.servicio = servicio;
	}
	
	@PostMapping
	public ResponseEntity<?> crear(@RequestBody Pelicula pelicula){
		
		try {
			Pelicula creado = servicio.crear(pelicula);
			return ResponseEntity.status(HttpStatus.CREATED).body(creado);
			
		}catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al crear la pelicula");
		}
	}
	
	@GetMapping
	public ResponseEntity<?> listar(){
		
		try {
			List<Pelicula> peliculas = servicio.listar();
			return ResponseEntity.ok(peliculas);
		}catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al listar las peliculas");
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> buscar(@PathVariable Long id){
		
		try {
			
			Pelicula pelicula = servicio.buscarPorId(id);
			
			if(pelicula == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("La pelicula con el ID: "+ id +" ,no fue encontrado");
						
			}
			return ResponseEntity.ok(pelicula);
			
		}catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al listar las peliculas");
		}
	}
	
	@GetMapping("/genero")
	public ResponseEntity<?> buscarPorCargo(@RequestParam String genero){
		
		try {	
			List<Pelicula> pelicula = servicio.buscarPorGenero(genero);
			return ResponseEntity.ok(pelicula);
		}catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al buscar por genero");
		}
		
	}
	
	@GetMapping("/disponible")
	public ResponseEntity<?> buscarPorEstado(@RequestParam boolean disponible){
		
		try {	
			List<Pelicula> pelicula = servicio.buscarPorDisponible(disponible);
			return ResponseEntity.ok(pelicula);
		}catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al buscar la pelicula disponible");
		}
		
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> actualizar(@PathVariable Long id,@RequestBody Pelicula pelicula){
		
		try {
		
			Pelicula actualizada = servicio.actualizar(id, pelicula);
			
			if(actualizada == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("La pelicula con el ID: "+ id +" ,no fue encontrado");
			}
			return ResponseEntity.ok(actualizada);
			
		}catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar la pelicula");
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> eliminar(@PathVariable long id){
		
		try {
			
			boolean eliminado = servicio.eliminar(id);
			
			if(!eliminado) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body("La pelicula con el ID: "+ id +" ,no fue encontrado");
			}
			return ResponseEntity.ok("Pelicula eliminada con exito");
			
		}catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error al elimninar");
		}
	}
	
}
