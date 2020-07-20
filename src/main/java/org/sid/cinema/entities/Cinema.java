package org.sid.cinema.entities;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity @Data @AllArgsConstructor
@NoArgsConstructor @ToString
public class Cinema implements Serializable {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id ;
	@Column(length = 50)
	@NotNull
	@Size(min=3,max= 15)
	private String name;
	private double longitude, latitude, altitude ;
	@Max(15) @Min(1)
	private int nombreSalles ;
	@OneToMany(mappedBy = "cinema")
	private Collection<Salle> salles;
	@ManyToOne
	private Ville ville;
	
}
