package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import models.AuthRequest;
import models.AuthResponse;
import service.MyUserDetailsService;
import util.JwtUtil;

@RestController
public class LoginController {

	@Autowired
	private AuthenticationManager authManager;
	
	@Autowired
	private MyUserDetailsService myUserDetailsService;
	
	@Autowired
	private JwtUtil jwtTokenUtil;
	
	
	
	@RequestMapping(value="/authenticate",method=RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthRequest req)throws Exception{
	System.out.println("Request reached here");
		try {
		authManager
		.authenticate(new UsernamePasswordAuthenticationToken(req.getUsername(),req.getPassword()));
		}
		catch(BadCredentialsException e) {
			throw new Exception("Incorrect username ",e);
		}
		
		final UserDetails userDetails = myUserDetailsService.loadUserByUsername(req.getUsername());
		
		final String jwt = jwtTokenUtil.generateToken(userDetails);
		
		return ResponseEntity.ok(new AuthResponse(jwt));
	}
}
