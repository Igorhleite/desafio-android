# PicPay - Desafio Android

<img src="https://github.com/mobilepicpay/desafio-android/blob/master/desafio-picpay.gif" width="300"/>

Um dos desafios de qualquer time de desenvolvimento é lidar com código legado e no PicPay isso não é diferente. Um dos objetivos de trazer os melhores desenvolvedores do Brasil é atacar o problema. Para isso, essa etapa do processo consiste numa proposta de solução para o desafio abaixo e você pode escolher a melhor forma de resolvê-lo, de acordo com sua comodidade e disponibilidade de tempo:
- Resolver o desafio previamente, e explicar sua abordagem no momento da entrevista.
- Resolver o desafio durante a entrevista, fazendo um pair programming interativo com os nossos devs, guiando o desenvolvimento.

Com o passar do tempo identificamos alguns problemas que impedem esse aplicativo de escalar e acarretam problemas de experiência do usuário. A partir disso elaboramos a seguinte lista de requisitos que devem ser cumpridos ao melhorar nossa arquitetura:

- Em mudanças de configuração o aplicativo perde o estado da tela. Gostaríamos que o mesmo fosse mantido.
- Nossos relatórios de crash têm mostrado alguns crashes relacionados a campos que não deveriam ser nulos sendo nulos e gerenciamento de lifecycle. Gostaríamos que fossem corrigidos.
- Gostaríamos de cachear os dados retornados pelo servidor.
- Haverá mudanças na lógica de negócios e gostaríamos que a arquitetura reaja bem a isso.
- Haverá mudanças na lógica de apresentação. Gostaríamos que a arquitetura reaja bem a isso.
- Com um grande número de desenvolvedores e uma quantidade grande de mudanças ocorrendo testes automatizados são essenciais.
  - Gostaríamos de ter testes unitários testando nossa lógica de apresentação, negócios e dados independentemente, visto que tanto a escrita quanto execução dos mesmos são rápidas.
  - Por outro lado, testes unitários rodam em um ambiente de execução diferenciado e são menos fiéis ao dia-a-dia de nossos usuários, então testes instrumentados também são importantes.

Boa sorte! =)


### PicPay - Desafio Android - Meus resultados

Foram definidos alguns pré requisitos a serem cumpridos no desafio, a fim de cumprir tais pré requisitos foram ultilizados algumas tecnologias:

* [Coil - Image Loader](https://coil-kt.github.io/coil/getting_started/)
* [Navigation](https://developer.android.com/guide/navigation/navigation-getting-started)
* [Hilt](https://developer.android.com/training/dependency-injection/hilt-android?hl=pt-br)
* [Retrofit](https://square.github.io/retrofit/)
* [Gson](https://github.com/google/gson)
* [Coroutines](https://developer.android.com/kotlin/coroutines)
* [Room](https://developer.android.com/training/data-storage/room)
* [Mockk](https://mockk.io/)
* [Kotlinx-Couroutines-Test](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-test/)
* [Espresso](https://developer.android.com/training/testing/espresso)
* [Fragment Testing](https://developer.android.com/training/basics/fragments/testing?hl=pt-br)


#### Figma

A fim de tornar o desenvolvimento da interface mais eficaz um protópipo foi desenvolvido no figma:
* [Protópito Figma](https://www.figma.com/file/YXpJUv8FDM5mbetRPQtJO9/Desafio---PicPay-Android?node-id=0%3A1)

#### Resultados
<p float="left">
<img src="https://github.com/LeiteHIgor/desafio-android/blob/dev/imgs/open.gif" width="300"/>
<img src="https://github.com/LeiteHIgor/desafio-android/blob/dev/imgs/share.gif" width="300"/>
<img src="https://github.com/LeiteHIgor/desafio-android/blob/dev/imgs/detail.gif" width="300"/>
<img src="https://github.com/LeiteHIgor/desafio-android/blob/dev/imgs/error_refresh.gif" width="300"/>
<img src="https://github.com/LeiteHIgor/desafio-android/blob/dev/imgs/error_not_db.gif" width="300"/>
</p>
