package com.springframework.springfundamental.service;

import com.springframework.springfundamental.dto.keyboard.PostKeyboardRequest;
import com.springframework.springfundamental.dto.keyboard.PutKeyboardRequest;
import com.springframework.springfundamental.entity.Keyboard;
import com.springframework.springfundamental.repository.KeyboardRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class KeyboardServiceTest {

    @Mock
    private KeyboardRepository keyboardRepository;

    @InjectMocks
    private KeyboardService keyboardService;

    @Captor
    private ArgumentCaptor<Keyboard> keyboardArgumentCaptor;

    //mockKeyboardId
    private final UUID mockKeyboardId = UUID.fromString("7454efcf-732c-4873-be9e-c0acb34f0802");

    //mockKeyboard
    private Keyboard mockKeyboard(UUID keyboardId){

        var keyboard = new Keyboard();
        keyboard.setId(keyboardId);
        keyboard.setCategoryId(UUID.fromString("578aea3e-506f-4f02-9a66-a48124212d4a"));
        keyboard.setName("DineshMi");
        keyboard.setQuantity(10);
        keyboard.setPrice(BigDecimal.valueOf(10.25));
        keyboard.setLastOpId(UUID.fromString("e7075eee-2599-4f56-a669-f9e5bb507b0c"));

        return keyboard;
    }

    //GET ALL

    //GET BY ID
    @Test
    void testForGetKeyboardById(){
        // given
        var keyboardId = mockKeyboardId;
        var keyboard = mockKeyboard(keyboardId);

        //when
        when(keyboardRepository.findById(any(UUID.class))).thenReturn(Optional.of(keyboard));
        var actual = keyboardService.getKeyboardById(keyboardId.toString());

        //then
        verify(keyboardRepository, times(1)).findById(any(UUID.class));

        assertThat(actual).isNotNull();
        assertThat(actual.getId()).isEqualTo(keyboardId);
    }

    //CREATE
    //mockKeyboardRequest
    private PostKeyboardRequest mockPostKeyboardRequest() {
        return new PostKeyboardRequest("578aea3e-506f-4f02-9a66-a48124212d4a"
                ,"DineshMi",10,10.25
                ,"e7075eee-2599-4f56-a669-f9e5bb507b0c");
    }

    @Test
    void testForCreateCustomerKeyboard() {
        //given
        var keyboardRequest = mockPostKeyboardRequest();

        //when
        keyboardService.saveKeyboardVB(keyboardRequest);

        //then
        verify(keyboardRepository, times(1)).save(keyboardArgumentCaptor.capture());

        var actual = keyboardArgumentCaptor.getValue();
        assertThat(actual).isNotNull();
        assertThat(actual.getCategoryId()).isEqualTo(UUID.fromString(keyboardRequest.categoryId()));
        assertThat(actual.getName()).isEqualTo(keyboardRequest.name());
        assertThat(actual.getQuantity()).isEqualTo(keyboardRequest.quantity());
        assertThat(actual.getPrice()).isEqualTo(BigDecimal.valueOf(keyboardRequest.price()));
        assertThat(actual.getLastOpId()).isEqualTo(UUID.fromString(keyboardRequest.lastOpId()));
    }

    //UPDATE
    //mockPutKeyboardRequest
    private PutKeyboardRequest mockPutKeyboardRequest() {
        return new PutKeyboardRequest("8c195572-9fb0-4dee-a4a1-095b4ce79023"
                ,"ZhiyongHan",12,200.44
                ,"5d63a25e-8e9f-4115-a5c0-25b83e7c935d");
    }

    @Test
    void testForUpdateKeyboard() {
        //given
        var keyboardId = mockKeyboardId;
        var keybord = mockKeyboard(keyboardId);
        var keyboardRequest = mockPutKeyboardRequest();

        //when
        when(keyboardRepository.findById(keyboardId)).thenReturn(Optional.of(keybord));
        keyboardService.updateKeyboard(keyboardId.toString(),keyboardRequest);

        //then
        verify(keyboardRepository,times(1)).save(keyboardArgumentCaptor.capture());
        var actual = keyboardArgumentCaptor.getValue();

        assertThat(actual).isNotNull();
        assertThat(actual.getCategoryId()).isEqualTo(UUID.fromString(keyboardRequest.categoryId()));
        assertThat(actual.getName()).isEqualTo(keyboardRequest.name());
        assertThat(actual.getQuantity()).isEqualTo(keyboardRequest.quantity());
        assertThat(actual.getPrice()).isEqualTo(BigDecimal.valueOf(keyboardRequest.price()));
        assertThat(actual.getLastOpId()).isEqualTo(UUID.fromString(keyboardRequest.lastOpId()));
    }


    //DELETE
    @Test
    void testForDeleteKeyboard() {
        //given
        var keyboardId = mockKeyboardId;
        var keyboard = mockKeyboard(keyboardId);

        //when
        when(keyboardRepository.findById(any(UUID.class))).thenReturn(Optional.of(keyboard));
        keyboardService.deleteKeyboard(keyboardId.toString());

        //then
        verify(keyboardRepository, times(1)).delete(keyboard);
    }
}
