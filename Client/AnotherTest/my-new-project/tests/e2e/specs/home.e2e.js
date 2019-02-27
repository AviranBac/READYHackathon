describe('Home Page', () => {
  it('has the correct title and heading', () => {
    cy.visit('/')
    cy.title().should('equal', 'מערכת זי"ת')
    cy.contains('h1', 'Home Page')
  })
})
