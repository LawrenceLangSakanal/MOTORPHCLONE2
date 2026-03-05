package com.motorph.service;

import com.motorph.model.Employee;
import com.motorph.repo.EmployeeRepository;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

/**
 * Service for employee profile updates, including profile-photo management.
 */
public class ProfileService {

    private final EmployeeRepository repo;

    private static final String PHOTO_DIR = "src/com/gui/images/EmployeeIDs/";

    public ProfileService(EmployeeRepository repo) {
        this.repo = repo;
    }

    /**
     * Copy a photo from {@code srcFile} into the app's images directory
     * naming it {@code <empId>.<ext>}, then updates the employee's
     * {@code profilePhotoPath} in the repository.
     *
     * @return the destination file path
     * @throws IOException if the copy fails
     * @throws IllegalArgumentException if the file type is unsupported
     */
    public String uploadProfilePhoto(String empId, File srcFile) throws IOException {
        String name = srcFile.getName();
        int dotIdx = name.lastIndexOf('.');
        if (dotIdx < 0) throw new IllegalArgumentException("File has no extension.");
        String ext = name.substring(dotIdx + 1).toLowerCase();
        if (!ext.equals("png") && !ext.equals("jpg") && !ext.equals("jpeg")) {
            throw new IllegalArgumentException("Only PNG/JPG images are supported.");
        }

        File destDir = new File(PHOTO_DIR);
        if (!destDir.exists()) destDir.mkdirs();

        String destName = empId + "." + ext;
        File dest = new File(destDir, destName);
        Files.copy(srcFile.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);

        // Persist the path in the in-memory employee record
        repo.findById(empId).ifPresent(e -> e.setProfilePhotoPath(dest.getPath()));

        return dest.getPath();
    }

    /**
     * Update mutable profile fields (phone number, address) for an employee.
     */
    public boolean updateProfile(String empId, String phoneNumber, String address) {
        return repo.findById(empId).map(e -> {
            if (phoneNumber != null && !phoneNumber.isBlank()) e.setPhoneNumber(phoneNumber);
            if (address     != null && !address.isBlank())     e.setAddress(address);
            return repo.update(e);
        }).orElse(false);
    }
}
