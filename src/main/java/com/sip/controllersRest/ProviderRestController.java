package com.sip.controllersRest;

import com.sip.entities.Article;
import com.sip.entities.Provider;
import com.sip.exceptions.ResourceNotFoundException;
import com.sip.repositories.ArticleRepository;
import com.sip.repositories.ProviderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/rest/providers")
@CrossOrigin(origins = "*")
public class ProviderRestController {

    public static String uploadDirectory = System.getProperty("user.dir")+"/src/main/resources/static/uploads/logoProviders";
    public String trouve=null;
private final ProviderRepository providerRepository;
    private final ArticleRepository articleRepository;


    @Autowired
    public ProviderRestController(ProviderRepository providerRepository, ArticleRepository articleRepository) {
        this.providerRepository = providerRepository;
        this.articleRepository = articleRepository;
    }


    @GetMapping("/list")
    public List<Provider> listProviders() {
    	List<Provider> lp = (List<Provider>)providerRepository.findAll();
    	if(lp.size() == 0)
    		lp = null;
        return lp;
    }

    @GetMapping("/{providerId}")
    public Optional<Provider> getProvider(@PathVariable Long providerId) {
        return providerRepository.findById(providerId);
    }

    @PostMapping("/add")
    public Provider addProvider(@Valid@RequestBody Provider provider/*,@RequestParam("image") MultipartFile[] files*/) {

        if(provider.getName()=="")
        	provider.setName(null);

       /*MultipartFile file = files[0];
        Path fileNameAndPath = Paths.get(uploadDirectory, file.getOriginalFilename());
        try {
            Files.write(fileNameAndPath, file.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        StringBuilder fileName = new StringBuilder();
        fileName.append(file.getOriginalFilename());
        provider.setLogo(fileName.toString());*/

        providerRepository.save(provider);
        return provider;
    }
   /* @PostMapping("/addwithlogo")
    public Provider addProviderUplaodImage(@RequestParam("imageLogo") MultipartFile file,
                                           @RequestParam("name") String name,
                                           @RequestParam("email") String email,
                                           @RequestParam("address") String address,
                                           @RequestParam("telephone") String telephone,
                                           @RequestParam("logoName") String logo) throws IOException {

       // if (name.isEmpty())
         //   name=null;

    //    MultipartFile file = files[0];
        Path fileNameAndPath = Paths.get(uploadDirectory, file.getOriginalFilename());
        try {
            Files.write(fileNameAndPath, file.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        StringBuilder fileName = new StringBuilder();
        fileName.append(file.getOriginalFilename());

        Provider provider = new Provider(name, address, email, telephone, logo);

        providerRepository.save(provider);
        return provider;
    }
*/

       @DeleteMapping("/{providerId}")
    public ResponseEntity<?> deleteProvider(@PathVariable Long providerId) {
        return providerRepository.findById(providerId).map(provider -> {
            providerRepository.delete(provider);
            return ResponseEntity.ok().build();
        }).orElseThrow(() ->new ResourceNotFoundException("ProviderId " + providerId + " not found"));
    }

    @PutMapping("/{providerId}")
    public Provider updateProvider(@PathVariable Long providerId, @Valid@RequestBody Provider providerRequest) {
        return providerRepository.findById(providerId).map(
                provider -> {

                    provider.setName(providerRequest.getName());
                    provider.setAddress(providerRequest.getAddress());
                    provider.setEmail(providerRequest.getEmail());
                    provider.setTelephone(providerRequest.getTelephone());
                    provider.setLogo(providerRequest.getLogo());

                    return providerRepository.save(provider);
                }
        ).orElseThrow(() ->new ResourceNotFoundException("ProviderId " + providerId + " not found"));
    }


    @GetMapping("chercher/{mots}")
    public List<Provider> chercherProviders(@PathVariable String mots) {

        List<Provider> lp = (List<Provider>)providerRepository.findProviders(mots);
        if(lp.size() == 0)
            lp = null;

        return lp;

    }

}
