package com.banco.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;
import java.util.Optional;
import com.banco.auth.entity.Role;

public interface RoleRepository extends JpaRepository<Role, UUID> {
    /**
     * Metodos ya incluidos por JpaRepository
     * save(Role role) (Sirve tanto para registrar nuevos como para actualizar).
     *
     * findById(UUID id) (Busca por llave primaria).
     *
     * findAll() (Trae a todos los roles).
     *
     * deleteById(UUID id) (Borra de la base de datos).
     *
     * existsById(UUID id) (Verifica si el ID ya existe).
     */

    Optional<Role> findByRoleName(String roleName);
}
