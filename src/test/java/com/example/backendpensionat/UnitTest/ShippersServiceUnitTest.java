package com.example.backendpensionat.UnitTest;

import com.example.backendpensionat.DTO.ShippersDetailedDTO;
import com.example.backendpensionat.Models.Shippers;
import com.example.backendpensionat.Repos.ShippersRepo;
import com.example.backendpensionat.Services.Impl.ShippersServiceIMPL;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

public class ShippersServiceUnitTest {
    private final ShippersRepo shippersRepo = mock(ShippersRepo.class);
    URL localUrl = getClass().getClassLoader().getResource("./XmlJsonFiles/shippers.json");
    ShippersServiceIMPL sut;

    @BeforeEach()
    void setUp() {
        sut = new ShippersServiceIMPL(shippersRepo);
    }

    @Test
    void whenGetShippersShouldMapCorrectly() throws IOException {

        List<ShippersDetailedDTO> result = sut.getShippersFromJSON(localUrl);

        assertEquals(3, result.size());
        ShippersDetailedDTO exampleShipper = result.get(0);
        assertEquals(1, exampleShipper.externalId);
        assertEquals("birgitta.olsson@hotmail.com", exampleShipper.email);
        assertEquals("Svensson-Karlsson", exampleShipper.companyName);
        assertEquals("Erik Östlund", exampleShipper.contactName);
        assertEquals("painter", exampleShipper.contactTitle);
        assertEquals("Järnvägsallén 955", exampleShipper.streetAddress);
        assertEquals("Gävhult", exampleShipper.city);
        assertEquals("07427", exampleShipper.postalCode);
        assertEquals("Sverige", exampleShipper.country);
        assertEquals("070-569-3764", exampleShipper.phone);
        assertEquals("2634-25376", exampleShipper.fax);
    }

    @Test
    void getAndSaveShippersShouldInsertNewRecords() throws IOException {
        when(shippersRepo.findShippersByExternalId(Mockito.anyLong())).thenReturn(Optional.empty());

        sut.getAndSaveShippers(true);

        verify(shippersRepo, times(3)).save(argThat(shipper -> shipper.getId() == null));
    }

    @Test
    void getAndSaveContractCustomersShouldUpdateExistingRecords() throws IOException {
        Shippers existingCustomer = new Shippers();
        existingCustomer.setId(1L);
        existingCustomer.setExternalId(1L);

        when(shippersRepo.findShippersByExternalId(Mockito.anyLong())).thenReturn(Optional.empty());
        when(shippersRepo.findShippersByExternalId(1L)).thenReturn(Optional.of(existingCustomer));

        sut.getAndSaveShippers(true);

        verify(shippersRepo, times(2)).save(argThat(shipper -> shipper.getExternalId() != 1L));
        verify(shippersRepo, times(1)).save(argThat(shipper -> shipper.getExternalId() == 1L));
    }

}
