package com.lovaas.center.modelo.entidad;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class Unidad {
	private String id;
	private String nombre;
}
