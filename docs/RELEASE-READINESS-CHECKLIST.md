# Release Readiness Checklist – Mobile Trading App (first public release)

Every item must be ticked, or explicitly accepted as a risk by the PO, before a "go" decision.

## 1. Testing complete
- [ ] All Critical and High priority test cases executed (see `RISK-MATRIX.md`)
- [ ] Full regression passed on both iOS and Android, on the agreed real devices
- [ ] Automated smoke suite passing on the release build
- [ ] Exploratory testing done on new and changed areas
- [ ] Test summary report shared with the team

## 2. Money flows (release blockers)
- [ ] Deposit, withdrawal, buy and sell orders tested end to end
- [ ] Balances and transaction history match after every money transaction
- [ ] Interrupted payments tested (network loss, timeout, app closed): money is either completed or returned, and the status shown is correct
- [ ] Double tap / duplicate submission cannot create two orders or charge twice
- [ ] No open bug that shows a wrong amount, or loses or duplicates money

## 3. Security and privacy
- [ ] Login, two-factor authentication and session timeout tested
- [ ] Users can only see their own data (tested with separate test accounts)
- [ ] Security review completed, with no open Critical findings

## 4. Stability and performance
- [ ] No known crash in the key flows on the agreed devices
- [ ] Performance tested above the expected launch peak load
- [ ] Live price updates tested, including what users see when the feed is slow or stops

## 5. Bugs
- [ ] No open Critical bugs
- [ ] Any remaining High bugs reviewed and accepted by the PO, with a plan to fix
- [ ] Known issues listed in the release notes

## 6. Monitoring and support
- [ ] Error logging in place for failed transactions and crashes
- [ ] Alerts set up so the team is notified of errors straight away
- [ ] Support team briefed on known issues and how to escalate money-related problems

## 7. Release safety net
- [ ] Feature flags in place to switch off a feature quickly without a full rollback
- [ ] Rollback plan agreed and tested
- [ ] Post-release smoke test planned on the live app

## 8. App stores and compliance
- [ ] App store submissions approved (Apple App Store and Google Play)
- [ ] Legal, risk-warning and regulatory text checked for each region
- [ ] KYC (identity check) flow reviewed with the compliance team

## 9. Sign-off
| Role | Name | Decision | Date |
|---|---|---|---|
| QA | | Go / No-go | |
| Product Owner | | Go / No-go | |
| Engineering lead | | Go / No-go | |