package com.supportportal.controller;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.supportportal.domain.HttpResponse;
import com.supportportal.domain.LigneProduction;
import com.supportportal.exception.domain.CodeLigneProdExistException;
import com.supportportal.exception.domain.LigneProdNotFoundException;
import com.supportportal.exception.domain.UniteFabNotFoundException;
import com.supportportal.service.LigneProdService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;


public class LigneProdControllerTest {

    @Mock
    private LigneProdService ligneProdService;

    @InjectMocks
    private LigneProdController ligneProdController;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetSommesCadences() {
        List<Map<String, Object>> expected = new ArrayList<>();
        when(ligneProdService.getSommesCadences()).thenReturn(expected);

        List<Map<String, Object>> result = ligneProdController.getSommesCadences();

        assertEquals(expected, result);
    }

    @Test
    public void testAddNewLigneProd() throws CodeLigneProdExistException, LigneProdNotFoundException, UniteFabNotFoundException {
        String codeLp = "123";
        String robotTraitement = "Robot1";
        String observation = "Observation";
        String status = "true";
        String codeUf = "UF123";
        LigneProduction expected = new LigneProduction();

        when(ligneProdService.addLigneProd(eq(codeLp), eq(robotTraitement), eq(observation), anyBoolean(), eq(codeUf)))
                .thenReturn(expected);

        ResponseEntity<LigneProduction> response = ligneProdController.addNewLigneProd(codeLp, robotTraitement, observation, status, codeUf);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expected, response.getBody());
    }

    @Test
    public void testUpdateLigneProd() throws CodeLigneProdExistException, LigneProdNotFoundException, UniteFabNotFoundException {
        String codeLp = "123";
        String robotTraitement = "Robot1";
        String status = "true";
        LigneProduction expected = new LigneProduction();

        when(ligneProdService.updateLigneProd(eq(codeLp), eq(robotTraitement), anyBoolean()))
                .thenReturn(expected);

        ResponseEntity<LigneProduction> response = ligneProdController.updateLigneProd(codeLp, robotTraitement, status);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expected, response.getBody());
    }

    @Test
    public void testGetAllLignesProds() {
        List<LigneProduction> expected = new ArrayList<>();
        when(ligneProdService.getAllLigneProduction()).thenReturn(expected);

        ResponseEntity<List<LigneProduction>> response = ligneProdController.getAllLignesProds();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expected, response.getBody());
    }

   // @Test
    public void testDeleteLigneProd() throws IOException {
        String codeLp = "123";

        ResponseEntity<HttpResponse> expectedResponse = new ResponseEntity<>(HttpStatus.OK);
        doNothing().when(ligneProdService).deleteLigneProd(eq(codeLp));

        ResponseEntity<HttpResponse> response = ligneProdController.deleteLigneProd(codeLp);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResponse.getBody(), response.getBody());
    }
    
}
