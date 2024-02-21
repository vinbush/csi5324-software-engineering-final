package edu.baylor.propertypro;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Collections;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorViewResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.ModelAndView;

import edu.baylor.propertypro.data.ListingRepository;
import edu.baylor.propertypro.data.UserRepository;
import edu.baylor.propertypro.domain.Buyer;
import edu.baylor.propertypro.domain.Listing;
import edu.baylor.propertypro.domain.ListingType;
import edu.baylor.propertypro.domain.Offer;
import edu.baylor.propertypro.domain.Photo;
import edu.baylor.propertypro.domain.Realtor;
import edu.baylor.propertypro.domain.Request;
import edu.baylor.propertypro.dto.OfferDTO;
import edu.baylor.propertypro.dto.RequestDTO;

@SpringBootApplication
public class PropertyproApplication {

	public static void main(String[] args) {
		SpringApplication.run(PropertyproApplication.class, args);
	}
	
	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public ModelMapper modelMapper() {
		ModelMapper modelMapper = new ModelMapper();
		
		PropertyMap<Request, RequestDTO> requestMap = new PropertyMap<Request, RequestDTO>() {
			@Override
			protected void configure() {
				map().setHasResponse(source.hasResponse());
				map().setRealtorName(source.getListing().getRealtor().getName());
			}
		};
		PropertyMap<Offer, OfferDTO> offerMap = new PropertyMap<Offer, OfferDTO>() {
			@Override
			protected void configure() {
				map().setHasResponse(source.hasResponse());
				map().setRealtorName(source.getListing().getRealtor().getName());
			}
		};
		modelMapper.addMappings(requestMap);
		modelMapper.addMappings(offerMap);
		
		return modelMapper;
	}
	
	@Bean
	public CommandLineRunner dataLoader(ListingRepository listingRepo, UserRepository userRepo, PasswordEncoder encoder) {
		
		return new CommandLineRunner() {
			@Override
			public void run(String... args) throws Exception {
				Realtor realtor1 = new Realtor();
				realtor1.setName("George Realtor");
				realtor1.setPhone("9999999999");
				realtor1.setUsername("georgerealtor");
				realtor1.setEmail("realtorguy@realtor.com");
				realtor1.setPassword(encoder.encode("password"));
				realtor1.setStreet("1111 Broad");
				realtor1.setUnitNum("");
				realtor1.setCity("Montezuma");
				realtor1.setState("Iowa");
				realtor1.setZipCode("50171");
				realtor1.setAgency("Real Realty");
				realtor1.setYearsExperience(20);
				realtor1.setBrokerage("Real Brokerage");
				realtor1.setWebsite("real.com");
				Realtor persistedRealtor1 = userRepo.save(realtor1);
				
				Realtor realtor2 = new Realtor();
				realtor2.setName("Fred Realtor");
				realtor2.setPhone("9999999999");
				realtor2.setUsername("fredrealtor");
				realtor2.setEmail("fred@realtor.com");
				realtor2.setPassword(encoder.encode("password"));
				realtor2.setStreet("1131 South");
				realtor2.setUnitNum("");
				realtor2.setCity("Grinnell");
				realtor2.setState("Iowa");
				realtor2.setZipCode("50112");
				realtor2.setAgency("Awesome Realty");
				realtor2.setYearsExperience(20);
				realtor2.setBrokerage("Awesome Brokerage");
				realtor2.setWebsite("awesome.com");
				Realtor persistedRealtor2 = userRepo.save(realtor2);
				
				Buyer buyer1 = new Buyer();
				buyer1.setName("Bob Buyer");
				buyer1.setPhone("5555555555");
				buyer1.setUsername("buyerguy");
				buyer1.setEmail("buyerguy@buyer.com");
				buyer1.setPassword(encoder.encode("password"));
				buyer1.setStreet("2222 South");
				buyer1.setUnitNum("");
				buyer1.setCity("Grinnell");
				buyer1.setState("Iowa");
				buyer1.setZipCode("50112");
				buyer1.setDependents(1);
				buyer1.setMaritalStatus("Married");
				userRepo.save(buyer1);
				
				Resource resource = new ClassPathResource("house.jpg");
				InputStream stream = resource.getInputStream();
				
				String contentType = "image/jpeg"; //URLConnection.guessContentTypeFromName(file.getName());
				
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
		        int reads = stream.read();
		       
		        while(reads != -1){
		            baos.write(reads);
		            reads = stream.read();
		        }
				
				byte[] fileContent = baos.toByteArray();;
				
				Photo photo1 = new Photo();
				Photo photo2 = new Photo();
				Photo photo3 = new Photo();
				Photo photo4 = new Photo();
				
				photo1.setContentType(contentType);
				photo2.setContentType(contentType);
				photo3.setContentType(contentType);
				photo4.setContentType(contentType);
				
				photo1.setPicture(fileContent);
				photo2.setPicture(fileContent);
				photo3.setPicture(fileContent);
				photo4.setPicture(fileContent);
				
				Listing house1 = new Listing(ListingType.HOUSE, 200000, 2000, 1500, 2, 2, false, 1, "Lovely house", "Really nice", 41.616607, -92.560760, "1153 470th Avenue", "Montezuma", "IA", "50171");
				Listing house2 = new Listing(ListingType.HOUSE, 355000, 5000, 2500, 3, 3, true, 2, "Another house", "It's bigger than the last one and this is a longer description.", 41.585589, -92.519496, "703 East Main Street", "Montezuma", "IA", "50171");
				Listing house3 = new Listing(ListingType.HOUSE, 150000, 2000, 1200, 2, 2, false, 1, "Cheap house", "Quite a bargain, this is", 41.757953, -92.733107, "1915 Reed Street", "Grinnell", "IA", "50112");
				Listing house4 = new Listing(ListingType.HOUSE, 400000, 5000, 3000, 4, 4, true, 3, "Ritzy house", "Fancy house.", 41.752189,  -92.711301, "1709 10th Ave", "Grinnell", "IA", "50112");
				
				house1.setRealtor(persistedRealtor1);
				house2.setRealtor(persistedRealtor1);
				house3.setRealtor(persistedRealtor2);
				house4.setRealtor(persistedRealtor2);
				
				house1.addPhoto(photo1);
				house2.addPhoto(photo2);
				house3.addPhoto(photo3);
				house4.addPhoto(photo4);
				
				listingRepo.save(house1);
				listingRepo.save(house2);
				listingRepo.save(house3);
				listingRepo.save(house4);
			}
		};
	}
	
	// To avoid 404s when using Angular HTML 5 routing
	  @Bean
	  ErrorViewResolver supportPathBasedLocationStrategyWithoutHashes() {
	      return new ErrorViewResolver() {
	          @Override
	          public ModelAndView resolveErrorView(HttpServletRequest request, HttpStatus status, Map<String, Object> model) {
	              return status == HttpStatus.NOT_FOUND
	                      ? new ModelAndView("index.html", Collections.<String, Object>emptyMap(), HttpStatus.OK)
	                      : null;
	          }
	      };
	  }
}
