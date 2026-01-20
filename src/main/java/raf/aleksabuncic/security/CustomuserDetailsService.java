package raf.aleksabuncic.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import raf.aleksabuncic.domain.Veterinarian;
import raf.aleksabuncic.exception.ResourceNotFoundException;
import raf.aleksabuncic.repository.VeterinarianRepository;

@Service
@RequiredArgsConstructor
public class CustomuserDetailsService implements UserDetailsService {
    private final VeterinarianRepository veterinarianRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Veterinarian veterinarian = veterinarianRepository.findByLicenseNumber(Integer.parseInt(username))
                .orElseThrow(() -> new ResourceNotFoundException("No veterinarian found for license number " + username));

        return new User(
                veterinarian.getLicenseNumber().toString(),
                veterinarian.getPassword(),
                PermissionUtils.fromBitmask(veterinarian.getPermissions()));
    }
}
