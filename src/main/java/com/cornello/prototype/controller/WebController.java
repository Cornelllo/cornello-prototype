package com.cornello.prototype.controller;

import com.cornello.prototype.entity.AppUser;
import com.cornello.prototype.entity.Role;
import com.cornello.prototype.service.UserService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RequestMapping(value = "/api/v1")
@RestController
@RequiredArgsConstructor
public class WebController {

	private final UserService userService;

	@GetMapping("/users")
	public ResponseEntity<List<AppUser>> getUsers() {
		return ResponseEntity.ok().body(userService.getUsers());
	}

	@PostMapping("/users/save")
	public ResponseEntity<AppUser> saveUser(@RequestBody AppUser appUser) {
		URI uri = URI.create(ServletUriComponentsBuilder
				.fromCurrentContextPath()
				.path("/api/v1/users/save")
				.toUriString());
		return ResponseEntity.created(uri).body(userService.saveUser(appUser));
	}

	@PostMapping("/roles/save")
	public ResponseEntity<Role> saveRole(@RequestBody Role role) {
		URI uri = URI.create(ServletUriComponentsBuilder
				.fromCurrentContextPath()
				.path("/api/v1/roles/save")
				.toUriString());
		return ResponseEntity.created(uri).body(userService.saveRole(role));
	}

	@PostMapping("/users/assign-role")
	public ResponseEntity<Void> assignRole(@RequestBody AssignRoleForm form) {
		userService.assignRole(form.getUsername(),form.getRoleName());
		return ResponseEntity.ok().build();
	}

	@GetMapping("/users/by")
	public ResponseEntity<List<AppUser>> getUserByIdAndUsername(
		@RequestParam("id")String id,
		@RequestParam("username")String username) {
		return ResponseEntity.ok().body(userService.getUserByfullNameAndUsername(id,username));
	}

}

@Data
class AssignRoleForm {
	private String username;
	private String roleName;
}
