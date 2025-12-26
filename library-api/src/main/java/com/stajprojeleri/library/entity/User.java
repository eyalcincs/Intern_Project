package com.stajprojeleri.library.entity;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

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
public class User implements UserDetails {
		
		@Id
		@Column(name="id")
		@GeneratedValue(strategy = GenerationType.IDENTITY)  //Otomatik olarak id artırmak
		private Integer id;
		
		@Column(name = "name", nullable = false)
		private String name;
		
		@Column(name = "surname", nullable = false)
		private String surname;
		
		@Column(name = "username",unique = true, nullable = false)
		private String username;
		
		@Column(name = "email", nullable = false)
		private String email;
		
		@Column(name = "password", nullable = false)
		private String password;

		@Override
		public Collection<? extends GrantedAuthority> getAuthorities() {
			return List.of();
		}



		
		

}
