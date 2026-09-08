package com.vinteque.vinteque.service

import com.vinteque.vinteque.model.Message
import org.springframework.stereotype.Service
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter

@Service
class SseService {
    private var emitter: SseEmitter? = null

    fun connect(): SseEmitter{
        emitter = SseEmitter()

        emitter?.onCompletion {
            this.emitter=null
        }

        emitter?.onTimeout {
            this.emitter?.complete()
        }

        emitter?.onError {
            this.emitter=null
        }

        emitter

        return emitter!!
    }

    fun send(message : Message){
        emitter?.send(
            SseEmitter.event()
                .name("new_message")
                .data(message)
        )
    }
}