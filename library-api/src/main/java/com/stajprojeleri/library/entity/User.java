package com.stajprojeleri.library.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity

@Data
@AllArgsConstructor
@NoArgsConstructor

@Table(name = "user")
public class User {
		
		@Id
		@Column(name="id")
		@GeneratedValue(strategy = GenerationType.IDENTITY)  //Otomatik olarak id artırmak
		private Integer id;
		
		@Column(name = "usurname", nullable = false)
		private String username;
		
		@Column(name = "password", nullable = false)
		private String password;
		

}
